package com.example.springbootpractice.service;

import java.util.List;

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

    public List<Companies> findAll(){
        return this.companiesRepository.findAll();
    }

    public Companies findById(int id){
        return this.companiesRepository.findById(id).orElseThrow();
    }

    public void deleteById(int id){
         this.companiesRepository.deleteById(id);
    }

    public Companies editById(Companies c){
        Companies dbCompanies = this.companiesRepository.findById(c.getId()).orElseThrow();
        dbCompanies.setName(c.getName());
        dbCompanies.setUpdatedBy(c.getCreatedBy());
        return this.companiesRepository.save(dbCompanies) ;
    }
}
