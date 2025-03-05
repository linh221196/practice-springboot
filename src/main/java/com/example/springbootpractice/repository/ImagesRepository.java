package com.example.springbootpractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootpractice.domain.Images;

@Repository
public interface ImagesRepository extends JpaRepository<Images,Long> {
    
}
