package com.example.springbootpractice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.springbootpractice.domain.Users;
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

    public Users handleCreatedUser(Users user){
        return this.userRepository.save(user);
    }
    public List<Users> handleGetAllUser(){
        return this.userRepository.findAll();
    }
    public List<Users> handleGetAllUserByEmailAndPhone(String email, String phone){
        return this.userRepository.findByEmailAndPhone(email,phone);
    }
    public Optional<Users> findById(long id){
        return this.userRepository.findById(id);
    }
    public void handleDeleteUserById(long id){
         this.userRepository.deleteById(id);
    }
    
}
