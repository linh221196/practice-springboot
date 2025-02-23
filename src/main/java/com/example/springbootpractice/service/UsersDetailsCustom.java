package com.example.springbootpractice.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.springbootpractice.domain.Users;

@Component("userDetailsService")
public class UsersDetailsCustom implements UserDetailsService {
    private UserService userService;
    
    public UsersDetailsCustom(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = this.userService.findByEmail(username);
       return new User(
        users.getEmail(),
        users.getPassword(),
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
       ) ;
    }
    
}
 