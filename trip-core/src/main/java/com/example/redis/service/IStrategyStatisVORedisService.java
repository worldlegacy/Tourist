package com.example.redis.service;

import com.example.redis.vo.StrategyStatisVO;

import java.util.List;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/8
 */
public interface IStrategyStatisVORedisService {
    void viewnumIncrease(Long id);

    StrategyStatisVO getStrategyVO(Long id);

    void setStrategyVO(StrategyStatisVO vo);

    void replyIncrease(Long strategyId);

    Boolean isVOExists(Long id);

    List<StrategyStatisVO> queryVOByPattern(String s);

    boolean favor(Long sid, Long id);

    List<Long> getSidListByUserId(Long userId);

    boolean strategyThumbsup(Long sid, Long id);
}
