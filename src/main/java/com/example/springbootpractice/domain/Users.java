package com.example.springbootpractice.domain;

import java.util.List;


import com.example.springbootpractice.domain.entity.BaseEntity;
import com.example.springbootpractice.util.constantEnum.GenderEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String refreshToken;
    private String address;

    @ManyToOne
    @JoinColumn(name="roles_id")
    private Roles roles;

    @OneToMany(mappedBy = "users")
    private List<Orders> orders;

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + password + ", phone=" + phone + ", name=" + name
                + "]";
    }

    
    
}
