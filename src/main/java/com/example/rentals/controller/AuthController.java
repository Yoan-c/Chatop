package com.example.rentals.controller;

import com.example.rentals.entity.LogIn;
import com.example.rentals.entity.Register;
import com.example.rentals.entityDto.UserDto;
import com.example.rentals.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(
            summary = "sign up to chatop",
            description = "Send a Register object (name, email, password) to sign up")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Register.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "400")
    })
    @PostMapping("/register")
    public ResponseEntity<HashMap<String, String>> register(@RequestBody Register request){

        HashMap<String, String> token = new HashMap<>();

        authService.register(request);
        LogIn login = new LogIn(request.getEmail(), request.getPassword());
        token.put("token",authService.login(login));
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @Operation(
            summary = "login to chatop and get a token",
            description = "Get a token access by sending a Login object to login ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = LogIn.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "400")
    })
    @PostMapping("/login")
    public ResponseEntity<HashMap<String, String>> login(@RequestBody LogIn login){

        HashMap<String, String> token = new HashMap<>();

        token.put("token", authService.login(login));
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @Operation(
            summary = "Get user info",
            description = "get information about user account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401")
    })
    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe(){
        return new ResponseEntity<>(authService.getMyInfo(), HttpStatus.OK);
    }
}
