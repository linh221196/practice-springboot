package com.example.springbootpractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootpractice.domain.AuthCodes;


@Repository
public interface AuthCodesRepository extends JpaRepository<AuthCodes,Long> {
    public AuthCodes findByCode(String code);
}
