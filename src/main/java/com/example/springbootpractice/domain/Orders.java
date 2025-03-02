package com.example.springbootpractice.domain;

import java.util.List;

import com.example.springbootpractice.domain.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Orders extends BaseEntity {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private double totalPrice;

    @ManyToOne
    @JoinColumn
    private Users users;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private List<Products> products;
 
    
    
}
