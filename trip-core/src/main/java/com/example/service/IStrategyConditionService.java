package com.example.service;

import com.example.entity.StrategyCondition;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
public interface IStrategyConditionService extends IService<StrategyCondition> {

    /**
     * 攻略导航
     * @param type
     * @return
     */
    List<StrategyCondition> queryCondition(int type);
}
