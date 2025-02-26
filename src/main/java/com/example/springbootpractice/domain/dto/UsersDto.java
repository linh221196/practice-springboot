package com.example.springbootpractice.domain.dto;

import com.example.springbootpractice.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private String accessToken;
    private Users users;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor 
    public class InnerUsersDto {
        private String name;
        private String email;
        
    }
}
