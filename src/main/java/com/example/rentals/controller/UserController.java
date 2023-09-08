package com.example.rentals.controller;

import com.example.rentals.entityDto.UserDto;
import com.example.rentals.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserDto getUserInfoById(@PathVariable String id){
        return userService.getUserInfoById(id);
    }
}
