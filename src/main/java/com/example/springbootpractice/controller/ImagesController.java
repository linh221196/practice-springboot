package com.example.springbootpractice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springbootpractice.service.UserService;
import com.example.springbootpractice.util.SecurityJwt;

@RestController("/file")
public class ImagesController {
 
    private final UserService userService;

    public ImagesController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/upload-image")
     public ResponseEntity<String> uploadCompanyImages(@RequestParam("File") MultipartFile file) {
        String email = SecurityJwt.getCurrentUserLogin().get();
        long userId = this.userService.findByEmail(email).getId();
        String filePath = this.userService.saveProfileImage(userId, file);
        return ResponseEntity.ok(filePath);
    }
}
