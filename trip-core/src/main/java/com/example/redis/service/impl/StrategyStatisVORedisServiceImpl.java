package com.example.redis.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.entity.Strategy;
import com.example.redis.service.IStrategyStatisVORedisService;
import com.example.redis.util.RedisKeys;
import com.example.redis.vo.StrategyStatisVO;
import com.example.service.IStrategyService;
import com.example.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/8
 */
@Service
public class StrategyStatisVORedisServiceImpl implements IStrategyStatisVORedisService {
    @Autowired
    StringRedisTemplate template;
    @Autowired
    private IStrategyService strategyService;

    @Override
    public void viewnumIncrease(Long id) {
        //1 获取vo对象
        StrategyStatisVO vo = getStrategyVO(id);
        //2 vo对象的viewnum+1
        vo.setViewnum(vo.getViewnum() + 1);
        //3 将vo对象设置回redis
        setStrategyVO(vo);
    }

    @Override
    public void replyIncrease(Long strategyId) {
        //1 获取vo对象
        StrategyStatisVO vo = getStrategyVO(strategyId);
        //2 vo对象的viewnum+1
        vo.setReplynum(vo.getReplynum() + 1);
        //3 将vo对象设置回redis
        setStrategyVO(vo);
    }

    //2 存储vo对象
    public void setStrategyVO(StrategyStatisVO vo) {
        String key = RedisKeys.STRATEGY_STATIS_VO.join(vo.getStrategyId().toString());
        String value = JSON.toJSONString(vo);
        template.opsForValue().set(key, value);
    }


    //1 获取vo对象
    public StrategyStatisVO getStrategyVO(Long id) {
        String key = RedisKeys.STRATEGY_STATIS_VO.join(id.toString());
        StrategyStatisVO vo = null;
        if (!template.hasKey(key)) {
            //文章发出后，第一个访问文章的人操作,或者redis服务器因为各种原因丢失了数据
            vo = new StrategyStatisVO();
            Strategy s = strategyService.getById(id);
            vo.setViewnum(s.getViewnum());
            vo.setReplynum(s.getReplynum());
            vo.setSharenum(s.getSharenum());
            vo.setFavornum(s.getFavornum());
            vo.setThumbsupnum(s.getThumbsupnum());
            vo.setStrategyId(s.getId());
        } else {
            String s = template.opsForValue().get(key);
            vo = JSON.parseObject(s, StrategyStatisVO.class);

        }
        return vo;
    }

    @Override
    public Boolean isVOExists(Long id) {
        String key = RedisKeys.STRATEGY_STATIS_VO.join(id.toString());
        Boolean aBoolean = template.hasKey(key);
        return aBoolean;
    }

    @Override
    public List<StrategyStatisVO> queryVOByPattern(String s) {
        String key = RedisKeys.STRATEGY_STATIS_VO.join(s);
        Set<String> keys = template.keys(key);
        List<StrategyStatisVO> vos = new ArrayList<>();
        for (String k : keys) {
            String voJson = template.opsForValue().get(k);
            StrategyStatisVO vo = JSON.parseObject(voJson, StrategyStatisVO.class);
            vos.add(vo);
        }
        return vos;
    }

    //收藏
    @Override
    public boolean favor(Long sid, Long id) {
        //1 查询用户收藏的攻略id列表
        String key = RedisKeys.USER_STRATEGY_FAVOR.join(id.toString());
        List<Long> sidList = null;
        if (!template.hasKey(key)) {
            //redis中没有这个key，当前用户没有收藏过任何攻略
            sidList = new ArrayList<>();
        } else {
            String value = template.opsForValue().get(key);
            sidList = JSON.parseArray(value, Long.class);
        }
        //2 判断当前参数sid是否在攻略id列表中，如果在，取消收藏，收藏操作favornum-1,bi并且移除存在的sid。
        StrategyStatisVO vo = getStrategyVO(sid);
        if (sidList.contains(sid)) {
            //取消收藏
            vo.setFavornum(vo.getFavornum() - 1);
            //移除sid
            sidList.remove(sid);
        }
        //3 如果不在，表示当前是收藏操作，favornum+1，将sid添加到攻略id列表中
        else {
            vo.setFavornum(vo.getFavornum() + 1);
            sidList.add(sid);
        }
        //4 把vo数据和sidList数据写回到redis中
        setStrategyVO(vo);
        String sidListJson = JSON.toJSONString(sidList);
        template.opsForValue().set(key, sidListJson);
        //5 最终数组有sid证明收藏，没有证明取消收藏
        return sidList.contains(sid);
    }

    //收藏图标改变
    @Override
    public List<Long> getSidListByUserId(Long userId) {
        String key = RedisKeys.USER_STRATEGY_FAVOR.join(userId.toString());
        List<Long> sidList = null;
        if (!template.hasKey(key)) {
            sidList = new ArrayList<>();
        } else {
            String value = template.opsForValue().get(key);
            sidList = JSON.parseArray(value, Long.class);
        }
        return sidList;
    }

    //点赞/顶贴
    @Override
    public boolean strategyThumbsup(Long sid, Long id) {
        //1 生成key
        String key = RedisKeys.STRATEGY_THUMBSUP.join(id.toString(), sid.toString());
        //2 判断key在redis中是否存在，如果不存在，今天没点过赞，点赞数加1，key存入redis中
        if (!template.hasKey(key)) {
            StrategyStatisVO vo = getStrategyVO(sid);
            vo.setThumbsupnum(vo.getThumbsupnum() + 1);
            setStrategyVO(vo);
            Date now = new Date();
            Date end = DateUtil.getEndDate(now);
            Long dateBetween = DateUtil.getDateBetween(now, end);
            template.opsForValue().set(key, "1", dateBetween, TimeUnit.SECONDS);
            return true;
        } else {
            //3 存在，无需操作
            return false;
        }
    }
}
