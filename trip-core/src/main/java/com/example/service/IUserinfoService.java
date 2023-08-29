package com.example.service;

import com.example.entity.Userinfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
public interface IUserinfoService extends IService<Userinfo> {

    /**
     * 查询手机号是否存在
     */
    Boolean checkPhone(String phone);

    /**
     * 发送验证码
     */
    void sendVerifyCode(String phone);

    /**
     * 验证登录
     */
    void regist(String phone, String nickName, String password, String rpassword, String verifyCode);

    /**
     * 判断登录状态
     * @param username
     * @param password
     * @return
     */
    Userinfo login(String username, String password);
}
