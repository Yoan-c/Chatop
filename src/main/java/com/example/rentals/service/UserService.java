package com.example.rentals.service;

import com.example.rentals.entityDto.UserDto;
import com.example.rentals.entity.Users;
import com.example.rentals.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public UserDto getUserInfoById(String id) {
        int idUser = 0;
        try {
            idUser = Integer.parseInt(id);
        }
        catch (Exception ex){
            log.error("[UserService] getUserInfoById "+ex.toString());
        }

        Users user = userRepository.findById(idUser).orElseThrow();
        log.warn(user.toString());
        return new UserDto(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreated_at(),
                user.getUpdated_at());
    }

    public Users getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }
}
