package com.example.springbootpractice.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootpractice.domain.Users;
import com.example.springbootpractice.domain.dto.LoginDto;
import com.example.springbootpractice.domain.dto.UsersDto;
import com.example.springbootpractice.service.UserService;
import com.example.springbootpractice.util.SecurityJwt;

import jakarta.validation.Valid;


@RestController
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private final SecurityJwt securityJwt;

    @Value("${valid.refresh.jwt}")
    private long validRefreshTime;
    
    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService, SecurityJwt securityJwt) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
        this.securityJwt = securityJwt;
    }

    @PostMapping("/login")
    public ResponseEntity<UsersDto> login(@Valid @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        Users users = this.userService.findByEmail(loginDto.getUsername());
        
        this.securityJwt.createToken(auth);
        UsersDto usersDto = new UsersDto();
        String accessToken = this.securityJwt.createToken(auth);
        SecurityContextHolder.getContext().setAuthentication(auth);
        usersDto.setUsers(users);
        usersDto.setAccessToken(accessToken);

        String refreshToken = this.securityJwt.createRefreshToken(loginDto.getUsername(), usersDto.getUsers());      
        
        this.userService.updateUserRefreshToken(refreshToken, loginDto.getUsername());
        
        //set cookies
        ResponseCookie responseCookie = ResponseCookie
        .from("refreshToken", refreshToken)
        .httpOnly(true)
        .path("/")
        .maxAge(validRefreshTime)
        .secure(true)
        .build();
        return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE,responseCookie.toString())
        .body(usersDto);
    }
    
}
 