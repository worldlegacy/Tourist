package com.example.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult<T> {
    private Integer code; //操作代码
    private String msg;  //反馈信息
    //携带的数据：类型不定，所以使用泛型
    private T data;

    //成功反馈：无参
    public static JsonResult success() {
        return new JsonResult(200, "成功", null);
    }

    //成功反馈：有参
    public static <T> JsonResult success(T data) {
        return new JsonResult(200, "成功", data);
    }

    //失败反馈：无参
    public static JsonResult defaultError() {
        return new JsonResult(500, "请求移除", null);
    }

    //失败反馈：有参
    public static <T> JsonResult defaultError(T data) {
        return new JsonResult(500, "请求移除", data);
    }

    /**
     * 前端代码中使用501判断参数异常或业务异常
     */
    public static JsonResult paramError(String msg){
        return new JsonResult(501,msg,null);
    }
}
