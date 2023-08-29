package com.example.redis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Artist
 * @Description 存储统计数字对象
 * @Date 2023/8/8
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StrategyStatisVO {
    private Long strategyId;
    private int viewnum;
    private int replynum;
    private int favornum;
    private int sharenum;
    private int thumbsupnum;

}
