package com.example.springbootpractice.service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.example.springbootpractice.domain.AuthCodes;
import com.example.springbootpractice.domain.Users;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;
    private final UserService userService;
    private final AuthCodeService authCodeService;

    public EmailService(JavaMailSender javaMailSender,
        SpringTemplateEngine springTemplateEngine, UserService userService,
        AuthCodeService authCodeService
    ) {
        this.javaMailSender = javaMailSender;
        this.springTemplateEngine =springTemplateEngine;
        this.userService = userService;
        this.authCodeService = authCodeService;
    }

    public void sendOne(){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setCc("luu.khanh.linh.1034@gmail.com");
        msg.setSubject("Test mail from Spring boot");
        msg.setText("Hello world again =]]]]");
        this.javaMailSender.send(msg);
    }

    @Async
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

    @Async
    public void sendEmailWithTemplateSync(String to, String subject ,String templateName){
        Context context = new Context();
        int token = ThreadLocalRandom.current().nextInt(10000,99999);
        context.setVariable("token", token);
        String content = springTemplateEngine.process(templateName, context);

        
        this.sendEmailWithTemplate(to, subject, content, false, true);
    }

    @Async
    public void sendVerifyEmailWithTemplateSync(String toEmail, String subject ,String templateName){
        Context context = new Context();
        int token = ThreadLocalRandom.current().nextInt(10000,99999);
        String s = String.valueOf(token);
        Users user = this.userService.findByEmail(toEmail);
        String username = user.getName();
        AuthCodes authCodes = AuthCodes.builder()
        .code(s).users(user).expiredAt(Instant.now().plus(3, ChronoUnit.MINUTES))
        .isValid(true)
        .build();
        this.authCodeService.save(authCodes);
        String verificationLink = "http://localhost:8080/auth/verify?token=" + token;
        context.setVariable("verificationLink", verificationLink);
        context.setVariable("username", username);
        String content = springTemplateEngine.process(templateName, context);

        
        this.sendEmailWithTemplate(toEmail, subject, content, false, true);
    }
}
