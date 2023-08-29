package com.example.redis.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.entity.Userinfo;
import com.example.exception.LogicException;
import com.example.redis.service.IUserInfoRedisService;
import com.example.redis.util.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/3
 */
@Service
public class UserInfoRedisServiceImpl implements IUserInfoRedisService {

    //redis模板工具：用于向redis中存入值
    @Autowired
    private StringRedisTemplate template;

    //存储验证码
    @Override
    public void setVerifyCode(String phone, String code) {
        //拼接一个key
        String key = RedisKeys.REGIST_VERFY_CODE.join(phone);
        //将key存储到redis
        template.opsForValue().set(key,code,RedisKeys.REGIST_VERFY_CODE.getTime(), TimeUnit.SECONDS);
    }

    //获取验证码
    @Override
    public String getVerifyCode(String phone) {
        String key = RedisKeys.REGIST_VERFY_CODE.join(phone);
        String code = template.opsForValue().get(key);
        return code;
    }

    //存储token到redis中
    @Override
    public String setUser(Userinfo user) {
        //设置安全前缀
        String token = UUID.randomUUID().toString().replace("-", "");
        //设置key
        String key = RedisKeys.USER_LOGIN_TOKEN.join(token);
        //设置前端可接受的json格式字符串
        String value = JSON.toJSONString(user);
        //存储
        template.opsForValue().set(key,value,RedisKeys.USER_LOGIN_TOKEN.getTime(),TimeUnit.SECONDS);
        return token;
    }

    //查询token是否存在
    @Override
    public Userinfo getUserByToken(String token) {
        if(!StringUtils.hasLength(token)){
            return null;
        }
        String key = RedisKeys.USER_LOGIN_TOKEN.join(token);
        String value = template.opsForValue().get(key);
        //如果用户不存在
        if(value == null){
            return null;
        }else {
            //转成user对象再取出：后端需要读取user对象
            Userinfo userinfo = JSON.parseObject(value, Userinfo.class);
            //重置超时时间
            template.expire(key,RedisKeys.USER_LOGIN_TOKEN.getTime(),TimeUnit.SECONDS); //续命操作
            return userinfo;
        }
    }
}
