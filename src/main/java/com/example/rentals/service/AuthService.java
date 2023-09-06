package com.example.rentals.service;

import com.example.rentals.entity.LogIn;
import com.example.rentals.entity.Register;
import com.example.rentals.entity.Users;
import com.example.rentals.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public boolean register(Register request){
        Optional<Users> checkUser = userRepository.findByEmail(request.getEmail());
        if (request.getEmail() == null || request.getPassword() == null || request.getName() == null){
            log.error("Missing some user information ! code 400");
            log.warn(request.toString());
            // TODO : SEND AN ERROR MISSING SOME INFORMATION
            return false;
        }
        if (checkUser != null && checkUser.isPresent()) {
            log.error("User already exist !");
            log.warn(checkUser.get().toString());
            // TODO : SEND AN ERROR USER ALREADY EXISTS
            return false;
        }

        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return true;
    }

    public String login(LogIn login){
        if (login.getEmail() == null || login.getPassword() == null ){
            log.error("Check username and password!");
            log.warn(login.toString());
            // TODO : SEND AN ERROR MISSING SOME INFORMATION ABOUT Username ans Password
            return null;
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );
        Users user = userRepository.findByEmail(login.getEmail())
                .orElseThrow();
        String token = jwtService.generateToken(user);
        return token;
    }
}
