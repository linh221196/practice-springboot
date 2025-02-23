package com.example.springbootpractice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @NotBlank(message = "Username not blank")
    private String username;
    @NotBlank(message = "Password not blank")
    private String password;
}
