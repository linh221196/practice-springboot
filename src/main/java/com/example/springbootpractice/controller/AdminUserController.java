package com.example.springbootpractice.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springbootpractice.domain.Users;
import com.example.springbootpractice.domain.dto.PaginationDTO;
import com.example.springbootpractice.service.ImagesService;
import com.example.springbootpractice.service.UserService;
import com.example.springbootpractice.util.SecurityJwt;
import com.turkraft.springfilter.boot.Filter;

import jakarta.transaction.Transactional;




@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ImagesService imagesService;


    public AdminUserController(UserService userService, PasswordEncoder passwordEncoder, ImagesService imagesService){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.imagesService = imagesService;
      
    }


    @GetMapping("/")
    public String userPage(){
        return "Hello from Rest API";
    }

    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestBody Users user){
        String hashPW= user.getPassword();
        String hashed =  this.passwordEncoder.encode(hashPW);
    
        user.setPassword(hashed);
        this.userService.handleCreatedUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
        
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id){
         this.userService.handleDeleteUserById(id);
         return ResponseEntity.status(HttpStatus.OK).body("Delete Succeded");
    }

    
    @GetMapping("/{id}")
    public Users fetchUser(@PathVariable long id) throws BadCredentialsException{
        boolean isEmptyOptional=this.userService.findById(id).isPresent();
        if(!isEmptyOptional){
            throw new BadCredentialsException("ID INVALID EXCEPTION");
        }
        return this.userService.findById(id).get();

    }

    // http://localhost:8080/admin/users/all?page=1&size=3&filter=name~'a'and email~'h'&sort=id,asc
    @GetMapping("/all")
    public ResponseEntity<PaginationDTO> fetchAllUsers(
        @Filter Specification<Users> specification,Pageable pageable){
        return ResponseEntity.ok().body(this.userService.handleGetAllUser(specification,pageable));
    }

    @PutMapping("/edit")
    public Users creatUsers(@RequestBody Users user){
        Users tempUser = this.userService.findById(user.getId()).get();
        tempUser.setName(user.getName());
        tempUser.setEmail(user.getEmail());
        return this.userService.handleCreatedUser(tempUser);     
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("File") MultipartFile file) {
        String email = SecurityJwt.getCurrentUserLogin().get();
        long userId = this.userService.findByEmail(email).getId();
        String filePath = this.imagesService.saveProfileImage(userId, file);
        return ResponseEntity.ok(filePath);
    }

    @GetMapping(value="/profile-image",  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Transactional
    public ResponseEntity<Resource> getProfileImage() throws IOException {
        String email = SecurityJwt.getCurrentUserLogin().get();
        String filePath = this.userService.findByEmail(email).getImage();
        if (filePath == null || filePath.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        String baseDir = "C:/spring-next/springbootpractice";
        Path fileLocation = Paths.get(baseDir, filePath).toAbsolutePath();
        Resource image = new UrlResource(fileLocation.toUri());
        if (!image.exists() || !image.isReadable()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        String contentType = Files.probeContentType(fileLocation);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .body(image);
    }
}
