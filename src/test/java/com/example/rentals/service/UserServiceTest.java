package com.example.rentals.service;

import com.example.rentals.entity.Register;
import com.example.rentals.entity.UserInfo;
import com.example.rentals.entity.Users;
import com.example.rentals.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void getUserInfoById(){
        Register register = new Register("test2", "test2@gmail.com", "password");
        authService.register(register);
        Users user = userRepository.findByEmail(register.getEmail()).get();
        int idUser = user.getId();
        UserInfo userInfo = userService.getUserInfoById(String.valueOf(idUser));
        assertTrue(userInfo.getEmail().equals(user.getEmail()));
        userRepository.delete(user);
    }

    @Test
    @Transactional
    public void getUserInfoByIdErrorId(){
        Exception thrown = assertThrows(Exception.class, () ->
                userService.getUserInfoById(String.valueOf("x")));
        assertTrue(thrown.getMessage().contains("Unable"));

    }
}
