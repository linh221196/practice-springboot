package com.example.springbootpractice.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.example.springbootpractice.domain.Roles;
import com.example.springbootpractice.domain.Users;
import com.example.springbootpractice.service.UserService;
import com.example.springbootpractice.util.SecurityJwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

public class PermisssonInterceptor implements HandlerInterceptor{
    
    private final UserService userService;
    
    
    
    public PermisssonInterceptor(UserService userService) {
        this.userService = userService;
    }



    @Override
    @Transactional
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

        //check permisson
        String email = SecurityJwt.getCurrentUserLogin().get();
        Users user = this.userService.findByEmail(email);
        String role = user.getRoles().getName();
        Roles roles =user.getRoles();


        return true;
    }
}
