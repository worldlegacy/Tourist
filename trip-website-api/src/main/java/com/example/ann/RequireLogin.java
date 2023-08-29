package com.example.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Artist
 * @Description 登录拦截注解
 * @Date 2023/8/3
 */
//贴该注解方法：需要登陆后才能访问
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireLogin {

}
