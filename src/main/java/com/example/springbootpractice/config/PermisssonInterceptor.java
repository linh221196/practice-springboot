package com.example.springbootpractice.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PermisssonInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ){
        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        System.out.println("RUN preHandle ....");
        System.out.println("PATH " + path);
        System.out.println("httpMethod "+ httpMethod );
        System.out.println("requestURI "+ requestURI);

        return true;
    }
}
