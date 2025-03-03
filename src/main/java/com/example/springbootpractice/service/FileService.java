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
import com.example.springbootpractice.util.constantEnum.TypeUserEnum;

@Service
public class FileService {

    private final UserRepository userRepository;


    public FileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

        
    @Value("${baseDir}")
    private String UPLOAD_DIR;

        
    public String saveProfileImage(Long id, MultipartFile file, String type) {
        try {

            File uploadDir = new File(UPLOAD_DIR+type);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            String filename = id + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + filename);
            String fileLocale =filePath.toString();
            Images images= Images.builder().path(fileLocale).build();
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            if(type.equals(TypeUserEnum.USER)){
                Users user = this.userRepository.findById(id).orElseThrow();
                user.setImage(images);
                this.userRepository.save(user);
            }
            return fileLocale;  // Save this in the database
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

}
