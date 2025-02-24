package com.example.springbootpractice.domain;

import com.example.springbootpractice.domain.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Products extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String image;
    private String detailDesc;
    private String shortDesc;
    private long quantity;
    private long sold;
    private String factory;
    private String target;

    @ManyToOne
    @JoinColumn
    private Orders orders;

    @ManyToOne
    @JoinColumn
    private Companies companies;
  
    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", image=" + image + ", detailDesc=" + detailDesc
                + ", shortDesc=" + shortDesc + ", quantity=" + quantity + ", sold=" + sold + ", factory=" + factory
                + ", target=" + target + "]";
    }

    
}
