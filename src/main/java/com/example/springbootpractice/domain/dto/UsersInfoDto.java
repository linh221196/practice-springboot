package com.example.springbootpractice.domain.dto;

import com.example.springbootpractice.domain.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
@Builder
public class UsersInfoDto {
    private String accessToken;
    private String name;
    private String email;
    private Roles role;
    
}
