package com.example.springbootpractice.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String handleUserPage(){
        return "Hello world from UserService > UserController > /";
    }
}
