package com.example.job;

import com.example.redis.service.IStrategyStatisVORedisService;
import com.example.redis.vo.StrategyStatisVO;
import com.example.service.IStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/9
 */
@Component
public class RedisDataPersistenceJob {
    @Autowired
    private IStrategyStatisVORedisService strategyStatisVORedisService;
    @Autowired
    private IStrategyService strategyService;

    @Scheduled(cron = "0 * * * * ?")
    public void doWork(){
        //取出所有的vos：中间须要转成vo类
        List<StrategyStatisVO> vos = strategyStatisVORedisService.queryVOByPattern("*");
        //遍历vos
        for (StrategyStatisVO vo : vos) {
            //更新Strategy数据
            strategyService.updateStatisVO(vo);
        }
    }
}
