package com.example.springbootpractice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import com.example.springbootpractice.util.SecurityJwt;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Getter
@Setter
public class Companies {
// private final SecurityJwt securityJwt;


//     public Companies(SecurityJwt securityJwt) {
//     this.securityJwt = securityJwt;
// }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "COMPANY'S NAME CANNOT BLANK")
    private String name;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a",timezone = "GMT+7")

  
    private String createdBy;


    private String updatedBy;

    @OneToMany (mappedBy = "companies")
    private List<Products> products;

    @PrePersist
    public void handleBeforeCreate(){
        String creator = SecurityJwt.getCurrentUserLogin().orElse("");
        setCreatedBy(creator);
    }

}
