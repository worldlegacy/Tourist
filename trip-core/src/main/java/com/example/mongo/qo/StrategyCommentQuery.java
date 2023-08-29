package com.example.mongo.qo;

import com.example.qo.QueryObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/8
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StrategyCommentQuery extends QueryObject {
    private Long strategyId;
}
