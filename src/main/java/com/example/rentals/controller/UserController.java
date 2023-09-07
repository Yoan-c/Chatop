package com.example.rentals.controller;

import com.example.rentals.entity.UserInfo;
import com.example.rentals.repository.IUserRepository;
import com.example.rentals.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserInfo getUserInfoById(@PathVariable String id){
        return userService.getUserInfoById(id);
    }
}
