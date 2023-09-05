package com.example.rentals.controller;

import com.example.rentals.entity.LogIn;
import com.example.rentals.entity.Register;
import com.example.rentals.entity.Users;
import com.example.rentals.repository.IUserRepository;
import com.example.rentals.service.AuthService;
import com.example.rentals.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public void register(@RequestBody Register request){
        authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LogIn login){
        String token = authService.login(login);
        return token;
    }

}
