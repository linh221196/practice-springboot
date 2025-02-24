package com.example.springbootpractice.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.example.springbootpractice.domain.Users;
import com.example.springbootpractice.domain.dto.UsersDto;


@Service
public class SecurityJwt {

    public static final MacAlgorithm JWT_ALGORITHM =MacAlgorithm.HS512;
    public final JwtEncoder jwtEncoder;


    public SecurityJwt(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Value("${valid.access.jwt}")
    private long validTime;

    @Value("${valid.refresh.jwt}")
    private long validRefreshTime;

    public String createToken(Authentication auth){
        Instant now = Instant.now();
        Instant validity = now.plus(validTime, ChronoUnit.SECONDS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuedAt(now)
        .expiresAt(validity)
        .subject(auth.getName())
        .claim("jwtKey", auth)
        .build(); 

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }


    public String createRefreshToken(String email, Users users){
        Instant now = Instant.now();
        Instant validity = now.plus(validTime, ChronoUnit.SECONDS);

         Map<String, Object> userClaims = new HashMap<>();
            userClaims.put("id", users.getId());  
            userClaims.put("email", users.getEmail());
            userClaims.put("role", users.getRoles());


        JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuedAt(now)
        .expiresAt(validity)
        .subject(email)
        .claim("users", userClaims)
        .build(); 

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }



    public static Optional<String> getCurrentUserLogin(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static  String extractPrincipal(Authentication authentication){
        if(authentication == null){
            return null;
        }else if(authentication.getPrincipal() instanceof UserDetails userDetails){
            return userDetails.getUsername();
        }else if(authentication.getPrincipal() instanceof Jwt jwt){
            return jwt.getSubject();
        }else if( authentication.getPrincipal() instanceof String s){
            return s;
        }
        return null;
    }

    public  Optional<String> getCurrentUserJWT(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
        .filter(authentication -> authentication.getCredentials() instanceof String)
        .map(authentication -> (String) authentication.getCredentials());
    }
}
