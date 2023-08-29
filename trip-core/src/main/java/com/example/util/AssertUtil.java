package com.example.util;

import com.example.exception.LogicException;
import org.springframework.util.StringUtils;

/**
 * @Author Artist
 * @Description 断言工具类：判断参数有效性
 * @Date 2023/8/2
 */
public class AssertUtil {

    /**
     * 参数1：判断的字符串
     * 参数2：如果判断的字符串为空或空串，提示信息
     */
    public static void hasLength(String text,String message){

        if(!StringUtils.hasLength(text)){
            //抛异常
            throw new LogicException(message);
        }
    }

    /**
     * 判断两个字符串是否一致
     */
    public static void isEquals(String s1,String s2,String message){
        if(!s1.equals(s2)){
            throw new LogicException(message);
        }
    }
}
