package com.example.springbootpractice.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendOne(){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setCc("luu.khanh.linh.1034@gmail.com");
        msg.setSubject("Test mail from Spring boot");
        msg.setText("Hello world again =]]]]");
        this.javaMailSender.send(msg);
}
}
