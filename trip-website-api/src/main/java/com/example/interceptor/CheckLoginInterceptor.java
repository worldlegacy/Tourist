package com.example.interceptor;

import com.example.ann.RequireLogin;
import com.example.entity.Userinfo;
import com.example.exception.LogicException;
import com.example.redis.service.IUserInfoRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Artist
 * @Description 拦截器
 * @Date 2023/8/3
 */
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //放行跨域预请求
        if(!(handler instanceof HandlerMethod)){
            //直接放行
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //获取访问接口上是否有RequireLogin注解
        boolean b = handlerMethod.hasMethodAnnotation(RequireLogin.class);
        if(b){
            //需要登陆访问
            String token = request.getHeader("token");
            //判断是否有token
            Userinfo userinfo = userInfoRedisService.getUserByToken(token);
            if(userinfo == null){
                //用户没登陆过，拦截
                throw new LogicException("用户未登录");
            }else {
                //放行
                return true;
            }
        }else {
            return true;
        }
    }
}
