package com.example.springbootpractice.domain.dto;

import com.example.springbootpractice.domain.Users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersDto {
    private String accessToken;
    private Users users;
}
