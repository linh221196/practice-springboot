package com.example.springbootpractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PermisssonInterceptorConfiguration implements WebMvcConfigurer {
    @Bean
    PermisssonInterceptor getPermisssonInterceptor(){
        return new PermisssonInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        String[] whiteList = {
            "/","/login","/auth/refresh"
        };
        registry.addInterceptor(getPermisssonInterceptor())
        .excludePathPatterns(whiteList);
    }
}
