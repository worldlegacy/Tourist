package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/1
 */
@Configuration
//引入配置文件
@PropertySource("classpath:core.properties")
public class CoreConfig {

}
