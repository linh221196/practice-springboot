package com.example.springbootpractice.util.constantEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum VerifyConstant {
    
    EXPIRED("EXPIRED"),
    INVALID("INVALID"),
    OK("OK");

    private String status;

    public String getStatus(){
        return status;
    }
}
