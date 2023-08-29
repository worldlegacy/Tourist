package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author Artist
 * @Description mgr启动类
 * @Date 2023/8/1
 */
@SpringBootApplication
@EnableScheduling
public class MgrsiteApp {
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(MgrsiteApp.class,args);
    }
}