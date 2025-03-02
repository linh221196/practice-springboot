package com.example.springbootpractice.service;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;

    public EmailService(JavaMailSender javaMailSender,
        SpringTemplateEngine springTemplateEngine
    ) {
        this.javaMailSender = javaMailSender;
        this.springTemplateEngine =springTemplateEngine;
    }

    public void sendOne(){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setCc("luu.khanh.linh.1034@gmail.com");
        msg.setSubject("Test mail from Spring boot");
        msg.setText("Hello world again =]]]]");
        this.javaMailSender.send(msg);
    }


    public void sendEmailWithTemplate(
        String to, String subject, String content, boolean isMultipart, boolean isHtml
    ){
        MimeMessage message = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, isMultipart,StandardCharsets.UTF_8.name());
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);
            this.javaMailSender.send(message);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void sendEmailWithTemplateSync(String to, String subject ,String templateName){
        Context context = new Context();
        int token = ThreadLocalRandom.current().nextInt(10000,99999);
        context.setVariable("token", token);
        String content = springTemplateEngine.process(templateName, context);

        
        this.sendEmailWithTemplate(to, subject, content, false, true);
    }
}
