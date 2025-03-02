package com.example.springbootpractice;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

// @SpringBootApplication(
// 	exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
// 	//exclude = {ReactiveSecurityAutoConfiguration.class, ReactiveManagementWebSecurityAutoConfiguration.class }
// 	)
	@SpringBootApplication
	@EnableAsync
public class SpringbootpracticeApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringbootpracticeApplication.class, args);

	}

}
