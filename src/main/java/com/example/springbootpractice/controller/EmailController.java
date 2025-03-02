package com.example.springbootpractice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootpractice.service.EmailService;
import com.example.springbootpractice.util.annotation.ApiMessage;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/single")
    @ApiMessage("send one email")
    public void sendOneMail(){
        this.emailService.sendOne();
    }
}
