package com.example.springbootpractice;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
	exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
	//exclude = {ReactiveSecurityAutoConfiguration.class, ReactiveManagementWebSecurityAutoConfiguration.class }
	)
public class SpringbootpracticeApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringbootpracticeApplication.class, args);

	}

}
