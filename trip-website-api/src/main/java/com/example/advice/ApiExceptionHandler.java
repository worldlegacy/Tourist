package com.example.advice;

import com.example.exception.LogicException;
import com.example.util.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author Artist
 * @Description api的统一异常处理类
 * @Date 2023/8/2
 */

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * 捕获逻辑异常
     */
    @ExceptionHandler(LogicException.class)
    public JsonResult loginExceptionHandler(LogicException e) {
        e.printStackTrace();
        return JsonResult.paramError(e.getMessage());
    }

    /**
     * 捕获其他异常
     */
    @ExceptionHandler(RuntimeException.class)
    public JsonResult loginExceptionHandler(RuntimeException e) {
        e.printStackTrace();
        return JsonResult.paramError(e.getMessage());
    }

}
