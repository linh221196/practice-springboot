package com.example.springbootpractice.controller;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
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

import com.example.springbootpractice.domain.Users;
import com.example.springbootpractice.domain.dto.PaginationDTO;
import com.example.springbootpractice.service.UserService;
import com.turkraft.springfilter.boot.Filter;




@RestController
@RequestMapping("/admin/users")
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
}
