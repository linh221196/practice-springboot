package com.example.springbootpractice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootpractice.domain.Users;
import com.example.springbootpractice.service.UserService;
import com.example.springbootpractice.service.error.IdInvalidException;



@RestController
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public UserController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
      
    }


    @GetMapping("/")
    public String userPage(){
        return "Hello from Rest API";
    }

    @PostMapping("/users")
    public ResponseEntity<Users> createUser(@RequestBody Users user){
        String hashPW= user.getPassword();
        String hashed =  this.passwordEncoder.encode(hashPW);
        user.setPassword(hashed);
        this.userService.handleCreatedUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
        
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id){
         this.userService.handleDeleteUserById(id);
         return ResponseEntity.status(HttpStatus.OK).body("Delete Succeded");
    }

    
    @GetMapping("/users/{id}")
    public Users fetchUser(@PathVariable long id) throws IdInvalidException{
        boolean isEmptyOptional=this.userService.findById(id).isPresent();
        if(!isEmptyOptional){
            throw new IdInvalidException("ID INVALID EXCEPTION");
        }
        return this.userService.findById(id).get();

    }

    @GetMapping("/users/all")
    public List<Users> fetchAllUsers(){
        return this.userService.handleGetAllUser();
    }

    @PutMapping("/users/edit")
    public Users creatUsers(@RequestBody Users user){
        Users tempUser = this.userService.findById(user.getId()).get();
        tempUser.setName(user.getName());
        tempUser.setEmail(user.getEmail());
        return this.userService.handleCreatedUser(tempUser);     
    }
}
