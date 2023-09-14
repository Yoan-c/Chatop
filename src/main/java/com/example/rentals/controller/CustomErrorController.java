package com.example.rentals.controller;

import com.example.rentals.error.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RestController
public class CustomErrorController {

    public ResponseEntity<ApiError> CustomError(Object message, HttpStatus status) {
        ApiError error = new ApiError();
        error.setMessage(message);
        log.error("[CustomErrorController] CustomError : " + message);
        return ResponseEntity.status(status).body(error);
    }

    public ResponseEntity<?> CustomParamError(Object message, int status) {
        log.error("[CustomErrorController] CustomParamError : " + message);
        HashMap<String, Object> hashError = new HashMap<>();
        hashError.put("message", message);
        return ResponseEntity.status(status).body(hashError);
    }

}
