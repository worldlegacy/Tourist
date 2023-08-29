package com.example.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Artist
 * @Description 当类型为UserInfo的参数，贴有这个注解时，会自动注入当前登录对象
 * @Date 2023/8/8
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
