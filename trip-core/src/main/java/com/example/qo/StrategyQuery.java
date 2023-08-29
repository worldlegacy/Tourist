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
public class StrategyQuery extends QueryObject{
    private Long destId;
    private Long themeId;

    private String orderBy = "viewnum";
    private Integer type; //1 国内 2 国外 3 主题
    private Long refid; //当type = 1/2时，表示目的地id  当type=3时，表示主题id

}
