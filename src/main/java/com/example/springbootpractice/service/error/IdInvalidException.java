package com.example.springbootpractice.service.error;

public class IdInvalidException extends Exception {
    public IdInvalidException( String message){
        super(message);
    }
    
}
