package com.example.springbootpractice.util.constantEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum EmailConstant {
    VERIFY_EMAIL("Verify Email","registerVerifyMail"),
    WELCOME_EMAIL("Welcome to our website",null);

    private final String subject;
    private final String template;


    // EmailSubject(String subject,String template) {
    //     this.subject = subject;
    //     this.template = template;
    // }

    // public String getSubject() {
    //     return subject;
    // }
    
}
