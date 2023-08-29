package com.example.exception;

/**
 * @Author Artist
 * @Description 逻辑异常处理
 * @Date 2023/8/2
 */
public class LogicException extends RuntimeException{

    public LogicException(String msg){
        super(msg);
    }
}
