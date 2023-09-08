package com.example.rentals.service;

import com.example.rentals.entity.LogIn;
import com.example.rentals.entity.Register;
import com.example.rentals.entity.Users;
import com.example.rentals.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private IUserRepository userRepository;

    @Test
    void getTokenByLoginMissing(){
        LogIn login = new LogIn();
        BadCredentialsException badCredentialsException = assertThrows(
                BadCredentialsException.class,
                ()-> authService.login(login),
                "error");
        assertTrue(badCredentialsException.getMessage().contains("error"));
    }
    @Test
    void getTokenByLoginMissingField(){
        LogIn login = new LogIn("", "test");
        Exception thrown = assertThrows(BadCredentialsException.class, () -> authService.login(login));
      assertTrue(thrown.getMessage().contains("error"));
    }

    @Test
    @Transactional
    void getTokenByLoginCorrect(){
        Register register = new Register("test2", "test2@gmail.com", "password");
        authService.register(register);
        LogIn login = new LogIn("test2@gmail.com", "password");
        String token = authService.login(login);
        assertFalse(token.isEmpty());
    }

    @Test
    void setRegister(){
        Register register = new Register("test2", "test2@gmail.com", "password");
        authService.register(register);
        Optional<Users> users = userRepository.findByEmail(register.getEmail());
        assertTrue(users.get().getName().contains("test2"));
        userRepository.delete(users.get());
    }
    @Test
    void registerMissingArg(){
        Register register = new Register();
        register.setEmail("testUser@gmail.com");
        register.setPassword("password");

        Exception thrown = assertThrows(Exception.class, () ->
                authService.register(register));
        assertNull(thrown.getMessage());
    }

}
