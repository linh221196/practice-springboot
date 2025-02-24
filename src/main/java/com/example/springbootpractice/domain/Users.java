package com.example.springbootpractice.domain;

import java.util.List;

import com.example.springbootpractice.domain.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String password;
    private String phone;
    private String name;

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
