package com.example.springbootpractice.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.springbootpractice.domain.Users;
import com.example.springbootpractice.domain.dto.MetaDTO;
import com.example.springbootpractice.domain.dto.PaginationDTO;
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

    @Transactional
    public Users handleCreatedUser(Users user){
        try {
            return this.userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("EMAIL IS ALREADY IN USE");
        }
       
    }
    public PaginationDTO handleGetAllUser(
        Specification<Users> specification ,Pageable pageable
        ){
                
        Pageable pageableTemp = PageRequest.of(pageable.getPageNumber() -1, pageable.getPageSize(), pageable.getSort());

        Page<Users> users = this.userRepository.findAll(
          specification  
        ,pageableTemp
            );
        PaginationDTO paginationDTO = new PaginationDTO();
        MetaDTO metaDTO = new MetaDTO();
        metaDTO.setPage(users.getNumber()+1);
        metaDTO.setPageSize(users.getSize());
        metaDTO.setPages(users.getTotalPages());
        metaDTO.setTotal(users.getTotalElements());

        paginationDTO.setMetaDTO(metaDTO);
        paginationDTO.setResult(users.getContent());

        return paginationDTO ;
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
    public Users findByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public void updateUserRefreshToken(String token, String email){
        Users  currentUsers = this.findByEmail(email);
        if(currentUsers!= null){
            currentUsers.setRefreshToken(token);
            this.userRepository.save(currentUsers);
        }
    }
    public Users findByRefreshTokenAndEmail(String token, String email){
        return this.userRepository.findByRefreshTokenAndEmail(token, email); 
      }

    public void revokeRefreshToken(long id){
        Users user = this.userRepository.findById(id).orElseThrow();
        user.setRefreshToken(null);
        this.userRepository.save(user);
    }

    private static final String UPLOAD_DIR = "uploads/";

    public String saveProfileImage(Long userId, MultipartFile file) {
        try {

            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            String filename = userId + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            Users user = this.userRepository.findById(userId).orElseThrow();
            String fileLocale ="/uploads/" + filename;
            user.setImage(fileLocale);
            this.userRepository.save(user);
            return fileLocale;  // Save this in the database
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public Resource getProfileImage(String email) {
        try {
             String filePath = this.findByEmail(email).getImage();
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }
        String baseDir = "C:/spring-next/springbootpractice";
        Path fileLocation = Paths.get(baseDir, filePath).toAbsolutePath();
        Resource image = new UrlResource(fileLocation.toUri());
        if (!image.exists() || !image.isReadable()) {
            return null;
        }
        return image;
        } catch (Exception e) {
            throw new RuntimeException("Image not found", e);
        }
    }
}
