package com.example.springbootpractice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.example.springbootpractice.domain.entity.BaseEntity;


@Entity
@Getter
@Setter
public class Companies extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "COMPANY'S NAME CANNOT BLANK")
    private String name;

    @OneToMany (mappedBy = "companies")
    private List<Products> products;

}
