package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.StrategyRank;
import com.example.mapper.StrategyRankMapper;
import com.example.service.IStrategyRankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
@Service
public class StrategyRankServiceImpl extends ServiceImpl<StrategyRankMapper, StrategyRank> implements IStrategyRankService {

    @Autowired
    private StrategyRankMapper strategyRankMapper;

    //查询攻略访问排行
    @Override
    public List<StrategyRank> queryRank(int type) {
        QueryWrapper<StrategyRank> qw = new QueryWrapper<>();
        qw.eq("type",type);
        qw.inSql("statis_time","select max(statis_time) from strategy_rank");
        qw.last("limit 10");
        return strategyRankMapper.selectList(qw);
    }
}
