package com.example.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/4
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QueryObject {
    private Integer currentPage = 1; //当前页
    private Integer pageSize = 5; //每页显示条数
}
