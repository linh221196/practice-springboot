package com.example.springbootpractice.service;

import org.springframework.stereotype.Service;

import com.example.springbootpractice.util.SecurityJwt;

@Service
public class TrackService {
    public void setCreatedBy(String s){}
    public void setUpdatedBy(String s){}

     public void handleBeforeCreate(){
        String creator = SecurityJwt.getCurrentUserLogin().orElse("");
        setCreatedBy(creator);
    }

    public void handleBeforeUpdate(){
        String updatePerson = SecurityJwt.getCurrentUserLogin().orElse("");
        setUpdatedBy(updatePerson);
    }
}
