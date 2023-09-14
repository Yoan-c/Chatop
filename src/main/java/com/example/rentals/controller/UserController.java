package com.example.rentals.controller;

import com.example.rentals.entityDto.UserDto;
import com.example.rentals.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Operation(
            summary = "Get a user",
            description = "Get a user information with an ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401")
    })
    @GetMapping("/{id}")
    public UserDto getUserInfoById(@PathVariable String id){
        return userService.getUserInfoById(id);
    }
}
