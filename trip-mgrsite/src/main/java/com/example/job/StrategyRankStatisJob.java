package com.example.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Strategy;
import com.example.entity.StrategyRank;
import com.example.service.IStrategyRankService;
import com.example.service.IStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/7
 */

@Component
public class StrategyRankStatisJob {

    @Autowired
    private IStrategyRankService strategyRankService;
    @Autowired
    private IStrategyService strategyService;

    @Scheduled(cron="0/20 * * * * ?")
    void doWork(){
        List<Strategy> hotList = queryData("viewnum",null); //热门
        List<Strategy> abroadList = queryData("viewnum",1); //国外
        List<Strategy> chinaList = queryData("viewnum",0); //国内

        Date now = new Date();
        List<StrategyRank> hotRank = parseData(3, hotList, now);
        List<StrategyRank> abroadRank = parseData(1, abroadList, now);
        List<StrategyRank> chinaRank = parseData(2, chinaList, now);

        List<StrategyRank> all = new ArrayList<>();
        all.addAll(abroadRank);
        all.addAll(chinaRank);
        all.addAll(hotRank);
        //批量存储
        all.forEach(System.out::println);
//        strategyRankService.saveBatch(all);
    }

    /**
     * 查询攻略
     * column:表示查询排行榜时，使用哪列统计数据
     * isAbroad:null 热门 1 国外 0 国内
     */
    public List<Strategy> queryData(String column,Integer isabroad){
        QueryWrapper<Strategy> qw = new QueryWrapper<>();
        qw.orderByDesc(column);
        qw.eq(isabroad != null ,"isabroad",isabroad);
        qw.last("limit 10");
        List<Strategy> list = strategyService.list(qw);
        return list;
    }

    /**
     * 持久化值的存储
     */
    public List<StrategyRank> parseData(int type,List<Strategy> list,Date date){
        List<StrategyRank> ranks = new ArrayList<>();
        for (Strategy strategy : list) {
            StrategyRank rank = new StrategyRank();
            rank.setDestId(strategy.getDestId());
            rank.setDestName(strategy.getDestName());
            rank.setStrategyId(strategy.getId());
            rank.setStrategyTitle(strategy.getTitle());
            rank.setType(type);
            rank.setStatisTime(date);
            rank.setStatisnum(strategy.getViewnum().longValue());
            ranks.add(rank);
        }
        return ranks;
    }
}

