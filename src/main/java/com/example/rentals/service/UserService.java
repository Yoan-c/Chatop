package com.example.rentals.service;

import com.example.rentals.entity.UserInfo;
import com.example.rentals.entity.Users;
import com.example.rentals.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public UserInfo getUserInfoById(String id) {
        int idUser = 0;
        try {
            idUser = Integer.parseInt(id);
        }
        catch (Exception ex){
            log.error("[UserService] getUserInfoById "+ex.toString());
        }

        Users user = userRepository.getById(idUser);
        log.warn(user.toString());
        UserInfo userInfo = new UserInfo(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreated_at(),
                user.getUpdated_at());
        return userInfo;
    }
}
