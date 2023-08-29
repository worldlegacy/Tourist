package com.example.redis.service;

import com.example.redis.vo.BlackList;

import java.util.List;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/16
 */
public interface ISecurityService {
    boolean isAllowBrush(String key);

    void setTimes(String SKey);

    Long getTimes(String ip);

    void setMaxTime(String key);

    void setMaxTimeM(String key);

    boolean isRegistAllowBrush(String key);

    List<BlackList> addBlack();
}
