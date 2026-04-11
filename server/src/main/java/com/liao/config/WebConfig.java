package com.liao.config;

import com.liao.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePaths = Arrays.asList(
                "/user/login",
                "/user/register",
                "/class/list"
        );
        //不拦截登录和注册接口
        registry.addInterceptor(loginInterceptor).excludePathPatterns(excludePaths);
    }

    //测试用，先注释
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedHeaders("*");
       /* registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:3000", "https://students-system-coral.vercel.app")
                .allowedMethods("*")
                .allowCredentials(true)
                .allowedHeaders("*");*/
    }
}
