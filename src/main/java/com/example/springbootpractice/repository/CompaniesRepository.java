package com.example.springbootpractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootpractice.domain.Companies;

@Repository
public interface CompaniesRepository extends JpaRepository<Companies,Integer> {
     
}
