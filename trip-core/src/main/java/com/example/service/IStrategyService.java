package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Strategy;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.qo.StrategyQuery;
import com.example.redis.vo.StrategyStatisVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
public interface IStrategyService extends IService<Strategy> {

    /**
     * 查询显示的攻略
     * @param destId
     * @return
     */
    List<Strategy> queryViewnumTop3(Long destId);

    /**
     * 分页显示攻略信息
     * @param qo
     * @return
     */
    Page<Strategy> queryPage(StrategyQuery qo);

    /**
     * 保存添加的内容
     * @param strategy
     */
    void insert(Strategy strategy);

    /**
     * 更新
     * @param vo
     */
    void updateStatisVO(StrategyStatisVO vo);
}
