package com.example.rentals.service;

import com.example.rentals.entityDto.UserDto;
import com.example.rentals.entity.Users;
import com.example.rentals.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository ur){
        this.userRepository = ur;
    }

    public UserDto getUserInfoById(String id) {
        int idUser = 0;
        try {
            idUser = Integer.parseInt(id);
        }
        catch (Exception ex){
            log.error("[UserService] getUserInfoById "+ ex);
        }

        Users user = userRepository.findById(idUser).orElseThrow();
        return new UserDto(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }

    public Users getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }
}
