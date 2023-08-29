package com.example.service;

import com.example.entity.StrategyRank;
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
public interface IStrategyRankService extends IService<StrategyRank> {

    /**
     * 查询攻略排行
     * @param type
     * @return
     */
    List<StrategyRank> queryRank(int type);
}
