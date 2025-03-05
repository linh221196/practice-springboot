package com.example.springbootpractice.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.springbootpractice.domain.Images;
import com.example.springbootpractice.domain.Users;
import com.example.springbootpractice.repository.UserRepository;

@Service
public class ImagesService {

    private final UserRepository userRepository;


    public ImagesService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

        
    @Value("${baseDir}")
    private String UPLOAD_DIR;

        
    public String saveProfileImage(Long id, MultipartFile file) {
        try {

            File uploadDir = new File(UPLOAD_DIR+"USER");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            String filename = id + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + filename);
            String fileLocale =filePath.toString();
            Images images= Images.builder().path(fileLocale).build();
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
           
            Users user = this.userRepository.findById(id).orElseThrow();
            user.setImage(fileLocale);
            this.userRepository.save(user);
      
            return fileLocale;  // Save this in the database
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

}
