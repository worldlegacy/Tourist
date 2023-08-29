package com.example.es.qo;

import com.example.qo.QueryObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/15
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchQuery extends QueryObject {
    /**
     * 查全部 -1
     * 目的地 0
     * 攻略 1
     * 游记 2
     * 用户 3
     */
    private int type = -1;
    //查询的关键字
    private String keyword;
}
