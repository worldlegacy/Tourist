package com.example.interceptor;

import com.example.exception.LogicException;
import com.example.redis.service.ISecurityService;
import com.example.redis.util.RedisKeys;
import com.example.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/16
 */
@Component
public class BrushInterceptor implements HandlerInterceptor {
    @Autowired
    private ISecurityService securityService;

    //拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //跨域不拦截，直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //当前用户ip地址
        String ip = RequestUtil.getIPAddress();
        //当前操作的网页
        String url = request.getRequestURI().substring(1);
        //存储频刷操作的key
        String key = RedisKeys.BRUSH.join(url, ip);
        //存储违规操作的key
        String SKey = RedisKeys.BLACK.join(url+"a",ip);

        boolean b = false;
        if(url.equals("users/sendVerifyCode")){
            b = securityService.isRegistAllowBrush(key);
        }else {
            b = securityService.isAllowBrush(key);
        }

        if (b) {
            return true;
        } else {
            //添加违规次数：有则加一，没有则创建
            securityService.setTimes(SKey);
            //获取违规次数
            Long times = securityService.getTimes(SKey);
            if (times > 3 && times < 5) {
                //封禁该账号一天（频刷时间设置成24小时）
                securityService.setMaxTime(key);
            } else if (times > 5) {
                //封禁该账号一个月（频刷时间设置为一个月）
                securityService.setMaxTimeM(key);
            }
            throw new LogicException("您的操作已频繁");
        }
    }
}
