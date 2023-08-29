package com.example.resolver;

import com.example.ann.CurrentUser;
import com.example.entity.Userinfo;
import com.example.redis.service.IUserInfoRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/8
 */
@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private IUserInfoRedisService userInfoRedisService;
    /**
     * 哪些参数符合当前解析器的条件
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //有CurrentUser注解 和 属于UserInfo类
        boolean flg = methodParameter.getParameterType() == Userinfo.class &&
                                                            methodParameter.hasParameterAnnotation(CurrentUser.class);
        return flg;
    }

    /**
     * 给服务条件的参数注入当前登陆对象
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String token = request.getHeader("token");
        Userinfo user = userInfoRedisService.getUserByToken(token);
        return user;
    }
}
