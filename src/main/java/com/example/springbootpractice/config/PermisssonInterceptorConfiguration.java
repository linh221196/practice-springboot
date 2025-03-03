// package com.example.springbootpractice.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// import com.example.springbootpractice.service.UserService;

// @Configuration
// public class PermisssonInterceptorConfiguration implements WebMvcConfigurer {
//     private final UserService userService;
    
//     public PermisssonInterceptorConfiguration(UserService userService) {
//         this.userService = userService;
//     }
//     @Bean
//     PermisssonInterceptor getPermisssonInterceptor(){
//         return new PermisssonInterceptor(userService);
//     }
//     @Override
//     public void addInterceptors(InterceptorRegistry registry){
//         String[] whiteList = {
//             "/","/login","/auth/refresh",
//             "/v3/api-docs/**",
//         "/swagger-ui/**",
//         "/swagger-ui.html"
//         };
//         registry.addInterceptor(getPermisssonInterceptor())
//         .excludePathPatterns(whiteList);
//     }
// }
