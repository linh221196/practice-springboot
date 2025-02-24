package com.example.springbootpractice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    public Users handleCreatedUser(Users user){
        return this.userRepository.save(user);
    }
    public PaginationDTO handleGetAllUser(
        Specification<Users> specification ,Pageable pageable
        ){
        
        Page<Users> users = this.userRepository.findAll(
          specification  
        ,pageable
            );
        PaginationDTO paginationDTO = new PaginationDTO();
        MetaDTO metaDTO = new MetaDTO();
        metaDTO.setPage(users.getNumber());
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
    
}
