package com.example.springbootpractice.service;

import org.springframework.stereotype.Service;

import com.example.springbootpractice.domain.Companies;
import com.example.springbootpractice.repository.CompaniesRepository;

@Service
public class CompaniesService {
    
    private final CompaniesRepository companiesRepository;

    public CompaniesService(CompaniesRepository companiesRepository) {
        this.companiesRepository = companiesRepository;
    }

    public Companies save(Companies c){
        return this.companiesRepository.save(c);
    }
}
