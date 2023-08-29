package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Userinfo;
import com.example.exception.LogicException;
import com.example.mapper.UserinfoMapper;
import com.example.redis.service.IUserInfoRedisService;
import com.example.service.IUserinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.util.AssertUtil;
import com.example.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo> implements IUserinfoService {

    @Autowired
    private UserinfoMapper userinfoMapper;

    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    //业务：验证手机号
    @Override
    public Boolean checkPhone(String phone) {
        QueryWrapper<Userinfo> qw = new QueryWrapper<>();
        //设置根据手机号查询
        qw.eq("phone", phone);
        //根据手机号查询单个
        Userinfo userinfo = userinfoMapper.selectOne(qw);
        //false为不存在，可以注册
        return userinfo != null;
    }

    //业务：发送验证码
    @Override
    public void sendVerifyCode(String phone) {
        //生成随机验证码
        String code = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 4);
        //打印验证码到控制台
        System.err.println("验证码：" + code);
        //调用工具类实现发送验证码
//        SmsUtil.sendSmsAliYun(code,phone);  //上线后解开，频繁发送验证码

        //存储验证码
        userInfoRedisService.setVerifyCode(phone,code);

    }

    //业务：验证登录
    @Override
    public void regist( String phone, String nickName, String password, String rpassword, String verifyCode) {
        //参数有效性判断：调用自定义工具类
        AssertUtil.hasLength(phone,"手机号不能为空");
        AssertUtil.hasLength(nickName,"昵称不能为空");
        AssertUtil.hasLength(password,"密码不能为空");
        AssertUtil.hasLength(rpassword,"确认密码不能为空");
        AssertUtil.isEquals(password,rpassword,"两次密码输入不一致");
        AssertUtil.hasLength(verifyCode,"验证码不能为空");

        //判断手机号是否被注册
        if(checkPhone(phone)){
            throw new LogicException("手机号码已被注册");
        }
        //获取redis中存储的验证码
        String code = userInfoRedisService.getVerifyCode(phone);
        //判断验证码是否正确
        if(!code.equalsIgnoreCase(verifyCode) || code == null){
            throw new LogicException("验证码有误或已过期");
        }

        //实现注册功能
        Userinfo userinfo = new Userinfo();
        //传入值
        userinfo.setPhone(phone);
        userinfo.setNickname(nickName);
        userinfo.setPassword(password);
        //默认值
        userinfo.setEmail("请设置email");
        userinfo.setGender(0);
        userinfo.setLevel(0);
        userinfo.setCity("中国");
        userinfo.setHeadImgUrl("/images/default.jpg");
        userinfo.setInfo("你好安怡");
        userinfo.setState(0);
        //数据添加入数据库
        userinfoMapper.insert(userinfo);
    }

    //mysql验证账号密码
    @Override
    public Userinfo login(String username, String password) {
        //定义规则
        QueryWrapper<Userinfo> qw = new QueryWrapper<>();
        //设置规则
        qw.eq("phone",username);
        qw.eq("password",password);
        Userinfo userinfo = userinfoMapper.selectOne(qw);
        if(userinfo == null){
            throw new LogicException("账号或密码有错误");
        }
        return userinfo;
    }
}
