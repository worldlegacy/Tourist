package com.example.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author Artist
 * @Description 获取时间工具类
 * @Date 2023/8/9
 */
public class DateUtil {
    //获取传入Date对象的最后一秒
    public static Date getEndDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
//        c.set(Calendar.HOUR_OF_DAY, 23);
//        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    //获取两个日期之间的秒数
    public static Long getDateBetween(Date d1, Date d2) {
        long sec = (d1.getTime() - d2.getTime()) / 1000;
        return Math.abs(sec);
    }
}
