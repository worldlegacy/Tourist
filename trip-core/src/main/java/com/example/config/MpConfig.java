package com.example.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/1
 */
@Configuration
//主包名一致
@MapperScan("com.example.mapper")
//@MapperScan("com.example.*.mapper")
public class MpConfig {

    //分页拦截器
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }



}
