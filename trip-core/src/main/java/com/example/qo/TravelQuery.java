package com.example.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/10
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TravelQuery extends QueryObject{

    private Integer consumeType = -1;
    private Integer dayType = -1;
    private Integer travelTimeType = -1;

    private String orderBy = "create_time";

    private Long destId;
}
