package com.example.springbootpractice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.springbootpractice.domain.User;
import com.example.springbootpractice.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public String handleUserPage(){
        return "Hello world from UserService > UserController > /";
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleSaveCreatedUser(User user){
        return this.userRepository.save(user);
    }
    public List<User> handleGetAllUser(){
        return this.userRepository.findAll();
    }
    public List<User> handleGetAllUserByEmailAndPhone(String email, String phone){
        return this.userRepository.findByEmailAndPhone(email,phone);
    }
    public Optional<User> findById(long id){
        return this.userRepository.findById(id);
    }
    
}
