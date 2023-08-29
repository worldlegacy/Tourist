package com.example.redis.service;

import com.example.entity.Userinfo;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/3
 */
public interface IUserInfoRedisService {

    /**
     * redis：存储验证码
     * @param phone
     * @param code
     */
    void setVerifyCode(String phone, String code);

    /**
     * 获取redis中的验证码
     * @param phone
     * @return
     */
    String getVerifyCode(String phone);

    /**
     * 存储token到redis中
     * @param user
     * @return
     */
    String setUser(Userinfo user);

    /**
     * 查询token是否存在
     * @param token
     * @return
     */
    Userinfo getUserByToken(String token);
}
