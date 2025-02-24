package com.example.springbootpractice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootpractice.domain.Companies;
import com.example.springbootpractice.service.CompaniesService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        this.companiesService.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Companies>> getAll(){
        return ResponseEntity.ok().body(this.companiesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Companies> getById(@PathVariable int id){
        return ResponseEntity.ok().body(this.companiesService.findById(id));
    }

    @PutMapping("/edit")
    public ResponseEntity<Companies> editById(@RequestBody Companies c    ){
        return ResponseEntity.ok().body(this.companiesService.editById(c));
    }
    
}
