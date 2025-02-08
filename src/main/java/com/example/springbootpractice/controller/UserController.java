package com.example.springbootpractice.controller;

import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootpractice.service.UserService;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String userPage() {
        return 
        userService.handleUserPage();
    }
}
