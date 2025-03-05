package com.example.springbootpractice.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.example.springbootpractice.domain.AuthCodes;
import com.example.springbootpractice.domain.Users;
import com.example.springbootpractice.repository.AuthCodesRepository;
import com.example.springbootpractice.util.constantEnum.VerifyConstant;

@Service
public class AuthCodeService {
    private final AuthCodesRepository authCodesRepository;
    private final UserService userService;

    public AuthCodeService(AuthCodesRepository authCodesRepository,UserService userService) {
        this.authCodesRepository = authCodesRepository;
        this.userService=userService;
    }

    public AuthCodes findByCode(String code){
        return this.authCodesRepository.findByCode(code);
    } 
    public AuthCodes save(AuthCodes code){
        return this.authCodesRepository.save(code);
    }
    public void deleteById(long id){
        this.authCodesRepository.deleteById(id);
    }

    public String verifyRegister(String code){
       AuthCodes authCodes = this.authCodesRepository.findByCode(code);
       if(authCodes == null){return VerifyConstant.INVALID.getStatus();}
       long id = authCodes.getId();
       boolean isValid = authCodes.getExpiredAt().isAfter(Instant.now());
       if(!isValid){
        deleteById(id);
        VerifyConstant.EXPIRED.getStatus();}
       Users user = authCodes.getUsers();
       user.setValidated(true);
       this.userService.handleCreatedUser(user);
       deleteById(id);
       return VerifyConstant.OK.getStatus();
    }
}
