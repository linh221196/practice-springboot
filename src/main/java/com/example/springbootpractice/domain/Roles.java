package com.example.springbootpractice.domain;

import java.util.List;

import com.example.springbootpractice.domain.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Roles extends BaseEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;
    
    private String name;

    @OneToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Users> users;

    


}
