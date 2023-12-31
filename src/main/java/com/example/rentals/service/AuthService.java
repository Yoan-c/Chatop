package com.example.rentals.service;

import com.example.rentals.entity.LogIn;
import com.example.rentals.entity.Register;
import com.example.rentals.entityDto.UserDto;
import com.example.rentals.entity.Users;
import com.example.rentals.error.ApiCustomError;
import com.example.rentals.repository.UserRepository;
import com.example.rentals.utils.FunctionUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private  final FunctionUtils functionUtils;

    public AuthService( UserRepository ur,
                        PasswordEncoder pe,
                        JwtService js,
                        AuthenticationManager am,
                        FunctionUtils fu){
        this.userRepository = ur;
        this.passwordEncoder = pe;
        this.jwtService = js;
        this.authenticationManager = am;
        this.functionUtils = fu;
    }

    @Transactional
    public void register(Register request){
        Optional<Users> checkUser = userRepository.findByEmail(request.getEmail());
        if (request.getEmail() == null || request.getPassword() == null || request.getName() == null){
            log.error("[AuthService] register : Missing some user information !");
            throw new ApiCustomError(null, HttpStatus.BAD_REQUEST);
        }
        if (!functionUtils.isEmailValid(request.getEmail())){
            log.error("[AuthService] register : Please check your information");
            throw new ApiCustomError(null, HttpStatus.BAD_REQUEST);
        }
        if (checkUser != null && checkUser.isPresent()) {
            log.error("[AuthService] register : User already exist !");
            throw new ApiCustomError(null, HttpStatus.BAD_REQUEST);
        }
        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public String login(LogIn login){
        if (login.getEmail() == null || login.getPassword() == null ){
            log.error("[AuthService] login Check username and password!");
            throw new BadCredentialsException("error");
        }
        try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );
        } catch (BadCredentialsException ex){
            log.error("[AuthService] login "+ ex);
            throw new BadCredentialsException("error");
        }
        Users user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(() -> new BadCredentialsException("error"));
        return jwtService.generateToken(user);
    }

    public UserDto getMyInfo(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow();
        return new UserDto(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }
}
