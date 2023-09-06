package com.example.rentals.controller;

import com.example.rentals.entity.LogIn;
import com.example.rentals.entity.Register;
import com.example.rentals.entity.Users;
import com.example.rentals.repository.IUserRepository;
import com.example.rentals.service.AuthService;
import com.example.rentals.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @Operation(
            summary = "sign up to chatop",
            description = "Send a Register object (name, email, password) to sign up")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Register.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400")
    })
    @PostMapping("/register")
    public ResponseEntity<HashMap> register(@RequestBody Register request){

        HashMap<String, String> hashToken = new HashMap<>();

        authService.register(request);
        LogIn login = new LogIn(request.getEmail(), request.getPassword());
        hashToken.put("token",authService.login(login));
        return new ResponseEntity<>(hashToken, HttpStatus.OK);
    }

    @Operation(
            summary = "login to chatop and get a token",
            description = "Get a token access by sending a Login object to login ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = LogIn.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400")
    })
    @PostMapping("/login")
    public ResponseEntity<HashMap> login(@RequestBody LogIn login){

        HashMap<String, String> hashToken = new HashMap<>();

        hashToken.put("token", authService.login(login));
        return new ResponseEntity<>(hashToken, HttpStatus.OK);
    }

}
