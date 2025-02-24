package com.example.springbootpractice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
}
