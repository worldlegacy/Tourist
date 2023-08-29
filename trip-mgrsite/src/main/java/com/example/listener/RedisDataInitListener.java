package com.example.listener;

import com.example.entity.Strategy;
import com.example.redis.service.IStrategyStatisVORedisService;
import com.example.redis.vo.StrategyStatisVO;
import com.example.service.IStrategyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/9
 */
@Component
public class RedisDataInitListener implements ApplicationListener {
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IStrategyStatisVORedisService strategyStatisVORedisService;

    /**
     * 真实项目中，会将最新的一些文章同步到redis中
     */
    //redis初始化
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        //查询mysql中的攻略列表数据
        List<Strategy> list = strategyService.list();
        for (Strategy s : list) {
            //判断redis中是否存在数据
            Boolean b = strategyStatisVORedisService.isVOExists(s.getId());
            if(b){
                continue;
            }
            else {
                StrategyStatisVO vo = new StrategyStatisVO();
                //同名属性拷贝操作
                BeanUtils.copyProperties(s,vo);
                vo.setStrategyId(s.getId());
                strategyStatisVORedisService.setStrategyVO(vo);
            }
        }
        System.err.println("================ vo数据初始化完成 =====================");
    }

}
