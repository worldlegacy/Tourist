package com.example.controller;

import com.example.ann.RequireLogin;
import com.example.entity.Userinfo;
import com.example.redis.service.IStrategyStatisVORedisService;
import com.example.redis.service.IUserInfoRedisService;
import com.example.service.IUserinfoService;
import com.example.service.impl.UserinfoServiceImpl;
import com.example.util.JsonResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
//说明当前是什么接口
@Api(value = "用户控制器",description = "用户controller")
@RestController
@RequestMapping("/users")
public class UserinfoController {

    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IUserInfoRedisService userInfoRedisService;
    @Autowired
    private IStrategyStatisVORedisService strategyStatisVORedisService;

    //判断用户手机号是否存在
    @GetMapping("checkPhone")
    public boolean checkPhone(String phone) {
        Boolean ret = userinfoService.checkPhone(phone);
        return ret;
    }

    //发送验证码
    @GetMapping("/sendVerifyCode")
    public JsonResult sendVerifyCode(String phone) {
        userinfoService.sendVerifyCode(phone);
        return JsonResult.success();
    }

    //登录成功
    @PostMapping("/regist")
    public JsonResult regist(String phone,String nickname,String password,String rpassword,String verifyCode){
        userinfoService.regist(phone,nickname,password,rpassword,verifyCode);
        return JsonResult.success();
    }

    //登录操作
    @ApiImplicitParams({
            @ApiImplicitParam(value = "手机号",name = "username",dataType = "String",required = true),
            @ApiImplicitParam(value = "密码",name = "password",dataType = "String",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "用户登陆成功")
    })
    @ApiOperation(value = "登录",notes = "根据用户手机号和密码验证用户身份有效性")
    @PostMapping("/login")
    public JsonResult login(String username,String password){
        //mysql中进行账号密码的判断
        Userinfo user = userinfoService.login(username,password);
        //存储当前用户到redis
        String token = userInfoRedisService.setUser(user);
        //前端会获取map集合类型的值
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("user",user);
        return JsonResult.success(map);
    }

    //收藏图标改变
    @GetMapping("strategies/favor")
    @RequireLogin
    public JsonResult favor(Long sid,Long userId){
        List<Long> sidList = strategyStatisVORedisService.getSidListByUserId(userId);
        boolean contains = sidList.contains(sid);
        return JsonResult.success(contains);

    }
}

