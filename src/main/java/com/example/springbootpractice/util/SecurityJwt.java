package com.example.springbootpractice.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

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
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import com.example.springbootpractice.domain.Users;
import com.nimbusds.jose.util.Base64;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Service
public class SecurityJwt {

    public static final MacAlgorithm JWT_ALGORITHM =MacAlgorithm.HS512;
    public final JwtEncoder jwtEncoder;


    public SecurityJwt(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Value("${key.jwt}")
    private String jwtKey;

    @Value("${valid.access.jwt}")
    private long validTime;

    @Value("${valid.refresh.jwt}")
    private long validRefreshTime;

    private SecretKey getSecretKey(){
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes,0,keyBytes.length,JWT_ALGORITHM.getName());
    }

    public String createToken(String email){
        Instant now = Instant.now();
        Instant validity = now.plus(validTime, ChronoUnit.SECONDS);

        List<String> listAuthority = new ArrayList<String>();
        listAuthority.add("create");
        listAuthority.add("get");

        JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuedAt(now)
        .expiresAt(validity)
        .subject(email)
        .claim("users", email)
        .claim("permission", listAuthority)
        .build(); 

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }


    public String createRefreshToken(String email, Users users){
        Instant now = Instant.now();
        Instant validity = now.plus(validTime, ChronoUnit.SECONDS);
        ClaimRole claimRole = ClaimRole.builder()
        .id(users.getRoles().getId())
        .name(users.getRoles().getName())
        .build();

         Map<String, Object> userClaims = new HashMap<>();
            userClaims.put("id", users.getId());  
            userClaims.put("email", users.getEmail());
            userClaims.put("roles", claimRole);


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

    public static  Optional<String> getCurrentUserJWT(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
        .filter(authentication -> authentication.getCredentials() instanceof String)
        .map(authentication -> (String) authentication.getCredentials());
    }

    public Jwt checkValidRefreshToken(String token){
        NimbusJwtDecoder jwtDecoder =NimbusJwtDecoder.withSecretKey(getSecretKey()).macAlgorithm(JWT_ALGORITHM).build();
       
            try {
                 return jwtDecoder.decode(token);
            } catch (Exception e) {
               System.out.println("REFRESH TOKEN ERROR: " + e.getMessage());
               throw e;
            }
  
    }

    @Getter
    @Setter
    @Builder
    public static class ClaimRole {
        private long id;
        private String name;
   
    }

}
