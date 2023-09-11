package com.example.rentals.error;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
@Slf4j
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
