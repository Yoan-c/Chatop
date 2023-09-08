package com.example.rentals.error;

import com.example.rentals.controller.CustomErrorController;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
@NoArgsConstructor
public class ApiExceptionHandler extends RuntimeException {

    @Autowired
    private CustomErrorController customErrorController;


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex) {
        return customErrorController.CustomError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
        return customErrorController.CustomError(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ApiCustomError.class)
    public ResponseEntity<?> handleCustomException(ApiCustomError ex) {
        return customErrorController.CustomParamError(ex.getMsg(), ex.getHttpStatus().value());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return customErrorController.CustomParamError("An error has occurred please check the parameters",
                HttpStatus.BAD_REQUEST.value());
    }

}
