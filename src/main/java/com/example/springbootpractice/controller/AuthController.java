package com.example.springbootpractice.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootpractice.domain.Users;
import com.example.springbootpractice.domain.dto.LoginDto;
import com.example.springbootpractice.domain.dto.UsersDto;
import com.example.springbootpractice.domain.dto.UsersInfoDto;
import com.example.springbootpractice.service.AuthCodeService;
import com.example.springbootpractice.service.EmailService;
import com.example.springbootpractice.service.UserService;
import com.example.springbootpractice.util.SecurityJwt;
import com.example.springbootpractice.util.annotation.ApiMessage;
import com.example.springbootpractice.util.constantEnum.EmailConstant;

import jakarta.validation.Valid;


@RestController
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private final SecurityJwt securityJwt;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthCodeService authCodeService;

    @Value("${valid.refresh.jwt}")
    private long validRefreshTime;
    
    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService,
     SecurityJwt securityJwt,PasswordEncoder passwordEncoder, AuthCodeService authCodeService,
     EmailService emailService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
        this.securityJwt = securityJwt;
        this.passwordEncoder = passwordEncoder;
        this.emailService =emailService;
        this.authCodeService =authCodeService;
    }

    @GetMapping("/")
    public String hello(){
        return "hello";
    }

    @PostMapping("/register")
    public ResponseEntity<Users> createUser(@RequestBody Users user){
        String hashPW= user.getPassword();
        String hashed =  this.passwordEncoder.encode(hashPW);
    
        user.setPassword(hashed);
        this.userService.handleCreatedUser(user);
        String email = user.getEmail();
        this.emailService.sendVerifyEmailWithTemplateSync(email,EmailConstant.VERIFY_EMAIL.getSubject(), EmailConstant.VERIFY_EMAIL.getTemplate());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
        
    }

    @GetMapping("/auth/verify")
    public String verify(@RequestParam String token){
        String result = this.authCodeService.verifyRegister(token);  
        return result;
    }

    @PostMapping("/login")
    public ResponseEntity<UsersInfoDto> login(@Valid @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        Users users = this.userService.findByEmail(loginDto.getUsername());
        
        this.securityJwt.createToken(loginDto.getUsername());
        
        String accessToken = this.securityJwt.createToken(auth.getName());
        SecurityContextHolder.getContext().setAuthentication(auth);
       
        UsersInfoDto usersInfoDto = UsersInfoDto.builder()
        .email(users.getEmail())
        .accessToken(accessToken)
        .name(users.getName())
        .role(users.getRoles())
        .build();
        
        String refreshToken = this.securityJwt.createRefreshToken(loginDto.getUsername(), users);  
        this.userService.updateUserRefreshToken(refreshToken, loginDto.getUsername());


        
        //set cookies
        ResponseCookie responseCookie = ResponseCookie
        .from("refreshToken", refreshToken)
        .httpOnly(true)
        .path("/")
        .maxAge(validRefreshTime)
        .secure(true)
        .build();
        return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE,responseCookie.toString())
        .body(usersInfoDto);
    }
    
    @GetMapping("/auth/account")
    @ApiMessage("Get Auth Account")
    public ResponseEntity<Users> getAccount(){
        String email = SecurityJwt.getCurrentUserLogin().isPresent() ? SecurityJwt.getCurrentUserLogin().get() :"";
        Users users = this.userService.findByEmail(email);
        UsersDto usersDto = new UsersDto();
        usersDto.setUsers(users);

        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/auth/refresh")
    @ApiMessage("get user by refresh token")
    public ResponseEntity<UsersDto> getNewRefreshToken(
        @CookieValue(name = "refreshToken") String refreshToken
    ){
        Jwt jwt=this.securityJwt.checkValidRefreshToken(refreshToken);
        String email = jwt.getSubject();
      
        Users users = this.userService.findByRefreshTokenAndEmail(refreshToken, email);
        
        UsersDto usersDto = new UsersDto();
        usersDto.setUsers(users);
        String accessToken = this.securityJwt.createToken(email);
        usersDto.setAccessToken(accessToken);
        String newRefreshToken = this.securityJwt.createRefreshToken(email, users);      
        this.userService.updateUserRefreshToken(newRefreshToken, email);
        
        ResponseCookie responseCookie = ResponseCookie
        .from("refreshToken", newRefreshToken)
        .httpOnly(true)
        .path("/")
        .maxAge(validRefreshTime)
        .secure(true)
        .build();
        return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE,responseCookie.toString())
        .body(usersDto);
    }

    @GetMapping("/auth/logout")
    public ResponseEntity<Void> logout(
        @CookieValue(name = "refreshToken") String refreshToken
    ){
        Jwt jwt = this.securityJwt.checkValidRefreshToken(refreshToken);
        String email = jwt.getSubject();
        Users users = this.userService.findByRefreshTokenAndEmail(refreshToken, email);
        this.userService.revokeRefreshToken(users.getId());;
        
        ResponseCookie responseCookie = ResponseCookie
        .from("refreshToken",null)
        .httpOnly(true)
        .path("/")
        .maxAge(0)
        .secure(true)
        .build();

        return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE,responseCookie.toString())
        .body(null);
    }
}
 