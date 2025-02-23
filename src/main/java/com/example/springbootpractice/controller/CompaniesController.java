package com.example.springbootpractice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootpractice.domain.Companies;
import com.example.springbootpractice.service.CompaniesService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admin/companies")
public class CompaniesController {
    private final CompaniesService companiesService;
   
    public CompaniesController(CompaniesService companiesService) {
        this.companiesService = companiesService;
    }

    @PostMapping("/create")
    public ResponseEntity<Companies> create(@RequestBody Companies companies) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.companiesService.save(companies));
    }
    
}
