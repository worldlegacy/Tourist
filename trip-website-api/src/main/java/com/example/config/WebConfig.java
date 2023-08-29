package com.example.config;

import com.example.interceptor.BrushInterceptor;
import com.example.interceptor.CheckLoginInterceptor;
import com.example.interceptor.SignInterceptor;
import com.example.resolver.CurrentUserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author Artist
 * @Description 配置工具类
 * @Date 2023/8/3
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private CheckLoginInterceptor checkLoginInterceptor;
    @Autowired
    private CurrentUserArgumentResolver currentUserArgumentResolver;
    @Autowired
    private BrushInterceptor brushInterceptor;
    @Autowired
    private SignInterceptor signInterceptor;

    //配置登录拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //加入自定义拦截器
        registry.addInterceptor(checkLoginInterceptor)
                .addPathPatterns("/**") //拦截
                .excludePathPatterns("/users/**"); //放行
        registry.addInterceptor(brushInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(signInterceptor)
                .addPathPatterns("/**");
    }

    //配置参数解析器
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserArgumentResolver);
    }
}
