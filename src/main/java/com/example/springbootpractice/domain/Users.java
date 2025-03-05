package com.example.springbootpractice.domain;

import java.util.List;


import com.example.springbootpractice.domain.entity.BaseEntity;
import com.example.springbootpractice.util.constantEnum.GenderEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email
    @NotBlank(message = "EMAIL CANNOT BLANK")
    @Column(unique = true)
    private String email;

    private String password;

    private String phone;
    private String name;
    private int age;

  
    private String image;
    private boolean isValidated;
    private boolean isActive; 

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;

    @OneToMany(mappedBy = "users")
    private List<Addresses> address;

    @ManyToOne
    @JoinColumn(name="roles")
    private Roles roles;

    @OneToMany(mappedBy = "users")
    private List<Orders> orders;


    
    
}
