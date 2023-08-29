package com.example.redis.service.impl;

import com.example.exception.LogicException;
import com.example.redis.service.ISecurityService;
import com.example.redis.util.RedisKeys;
import com.example.redis.vo.BlackList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/16
 */
@Service
public class SecurityServiceImpl implements ISecurityService {

    @Autowired
    private StringRedisTemplate template;

    //通用页面频繁刷新：20秒
    @Override
    public boolean isAllowBrush(String key) {
        //setIfAbsent = setnx; 没有即创建，有则不做操作
        template.opsForValue().setIfAbsent(key, "10", RedisKeys.BRUSH.getTime(), TimeUnit.SECONDS);
        //次数减一 :decrement:使key对应的value-1
        Long ret = template.opsForValue().decrement(key);
        return ret >= 0;
    }

    //通用页面频繁刷新：注册页面专属：10秒
    @Override
    public boolean isRegistAllowBrush(String key) {
        //setIfAbsent = setnx; 没有即创建，有则不做操作
        template.opsForValue().setIfAbsent(key, "20", RedisKeys.BRUSH.getTime(), TimeUnit.SECONDS);
        //次数减一 :decrement:使key对应的value-1
        Long ret = template.opsForValue().decrement(key);
        return ret >= 0;
    }

    //黑名单数据持久化
    @Override
    public List<BlackList> addBlack() {
        Set<String> keys = template.keys(RedisKeys.BRUSH.getPrefix() + "*");
        List<BlackList> list = new ArrayList<>();
        for (String key : keys) {
            //获取redis中当前key的剩余时间：可设置单位
            Long time = template.getExpire(key, TimeUnit.SECONDS);
            BlackList blackList = new BlackList();
            blackList.setIpKey(key);
            blackList.setOpenTime(time);
            list.add(blackList);
        }
        return list;
    }

    //添加违规次数
    @Override
    public void setTimes(String SKey) {
        //redis中添加值为0的数据
        template.opsForValue().setIfAbsent(SKey, "0");
        //违规一次总次数加一
        template.opsForValue().increment(SKey);
    }

    //获取违规次数
    @Override
    public Long getTimes(String SKey) {
        return Long.parseLong(template.opsForValue().get(SKey));
    }

    //设置封禁时间：24小时
    @Override
    public void setMaxTime(String key) {
        template.opsForValue().set(key, "0", RedisKeys.BLACK_BRUSH.getTime(), TimeUnit.HOURS);
        throw new LogicException("封禁一天");
    }

    //设置封禁时间：一个月
    @Override
    public void setMaxTimeM(String key) {
        template.opsForValue().set(key, "0", RedisKeys.BLACK_BRUSH.getTime() * 30, TimeUnit.HOURS);
        throw new LogicException("封禁一个月");
    }

}
