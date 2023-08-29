package com.example.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/10
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TravelCondition {

    private int min;
    private int max;

    //出行天数
    public static final Map<Integer, TravelCondition> DAY_MAP = new HashMap<>();
    //平均消费
    public static final Map<Integer, TravelCondition> AVG_MAP = new HashMap<>();
    //出行时间
    public static final Map<Integer, TravelCondition> TIME_MAP = new HashMap<>();

    static {
        DAY_MAP.put(1, new TravelCondition(1, 3));
        DAY_MAP.put(2, new TravelCondition(4, 7));
        DAY_MAP.put(3, new TravelCondition(8, 14));
        DAY_MAP.put(4, new TravelCondition(15, Integer.MAX_VALUE));

        AVG_MAP.put(1, new TravelCondition(1, 999));
        AVG_MAP.put(2, new TravelCondition(1000, 5999));
        AVG_MAP.put(3, new TravelCondition(6000, 19999));
        AVG_MAP.put(4, new TravelCondition(20000, Integer.MAX_VALUE));

        TIME_MAP.put(1, new TravelCondition(1, 2));
        TIME_MAP.put(2, new TravelCondition(3, 4));
        TIME_MAP.put(3, new TravelCondition(5, 6));
        TIME_MAP.put(4, new TravelCondition(7, 8));
        TIME_MAP.put(5, new TravelCondition(9, 10));
        TIME_MAP.put(6, new TravelCondition(11, 12));
    }
}
