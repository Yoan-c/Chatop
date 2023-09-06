package com.example.rentals.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiCustomError extends RuntimeException{

    private String msg;
    private HttpStatus httpStatus;

    public ApiCustomError(String msg, HttpStatus httpStatus){
        super(msg);
        this.msg = msg;
        this.httpStatus = httpStatus;
    }
}
