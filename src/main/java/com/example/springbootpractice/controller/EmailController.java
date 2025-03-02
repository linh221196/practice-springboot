package com.example.springbootpractice.controller;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.ui.Model;
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

    @GetMapping("/template-mail")
    @ApiMessage("send one email with template")
    public void sendEmailWithTemplate(){
        
        this.emailService.sendEmailWithTemplateSync("luu.khanh.linh.1034@gmail.com", "W/ template", "verifyCode");
    }
}
