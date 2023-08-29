package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.StrategyCondition;
import com.example.mapper.StrategyConditionMapper;
import com.example.service.IStrategyConditionService;
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
public class StrategyConditionServiceImpl extends ServiceImpl<StrategyConditionMapper, StrategyCondition> implements IStrategyConditionService {

    @Autowired
    private StrategyConditionMapper strategyConditionMapper;
    @Override
    public List<StrategyCondition> queryCondition(int type) {
        QueryWrapper<StrategyCondition> qw = new QueryWrapper<>();
        qw.eq("type",type);
        qw.inSql("statis_time","select max(statis_time) from strategy_condition");
        qw.orderByDesc("count");
        return strategyConditionMapper.selectList(qw);
    }
}
