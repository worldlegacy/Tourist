package com.example.interceptor;

import com.example.exception.LogicException;
import com.example.util.Md5Utils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/16
 */
@Component
public class SignInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> param = new HashMap<>();
        for (String s : map.keySet()) {
            if ("sign".equalsIgnoreCase(s)) {
                continue;
            }
            String[] strings = map.get(s);
            //字符串数组转换成string字符串
            StringBuffer sb = new StringBuffer();
            for (String string : strings) {
                sb.append(string);
            }
            //前端取出sign的拼串
            param.put(s, sb.toString());
        }
        String server_sign = Md5Utils.signatures(param);
        String client_sign = request.getParameter("sign");

        //为了当前系统补救措施
        if(client_sign == null){
            return true;
        }
        if (client_sign != null && client_sign.equals(server_sign)) {
            String timestamp = request.getParameter("timestamp");
            //客户端浏览器发请求的时间
            long beginTime = Long.parseLong(timestamp);
            //服务器接收到请求的时间
            long endTime = new Date().getTime();

            if(endTime - beginTime > 30 * 1000){
                throw new LogicException("接口访问超时");
            }
            return true;
        } else {
            throw new LogicException("参数被篡改");
        }
    }
}
