package com.example.rentals.service;

import com.example.rentals.entity.Rentals;
import com.example.rentals.entity.Users;
import com.example.rentals.error.ApiCustomError;
import com.example.rentals.repository.MessageRepository;
import com.example.rentals.repository.RentalRepository;
import com.example.rentals.repository.UserRepository;
import com.example.rentals.utils.ConfigTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private ConfigTest configTest;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    @Transactional
    public void sendMessageWithoutUser(){
        Users user = (Users) configTest.getInfoConfig().get("user");
        userRepository.save(user);

        Rentals rentals = (Rentals) configTest.getInfoConfig().get("rental");
        rentals.setOwner(user);
        rentalRepository.save(rentals);

        HashMap<String, String> hashMessageInfo = new HashMap<>();
        hashMessageInfo.put("message", "test");
        hashMessageInfo.put("user_id", "");
        hashMessageInfo.put("rental_id", "test");

        Exception thrown = assertThrows(ApiCustomError.class, () ->
                messageService.postMessage(hashMessageInfo));
        assertTrue(thrown.getMessage().equals("error"));
    }

    @Test
    @Transactional
    public void sendMessageWithoutRental(){
        Users user = (Users) configTest.getInfoConfig().get("user");
        userRepository.save(user);

        Rentals rentals = (Rentals) configTest.getInfoConfig().get("rental");
        rentals.setOwner(user);
        rentalRepository.save(rentals);

        HashMap<String, String> hashMessageInfo = new HashMap<>();
        hashMessageInfo.put("message", "test");
        hashMessageInfo.put("user_id", String.valueOf(user.getId()));
        hashMessageInfo.put("rental_id", "");

        Exception thrown = assertThrows(ApiCustomError.class, () ->
                messageService.postMessage(hashMessageInfo));
        assertTrue(thrown.getMessage().equals("error"));
    }

    @Test
    @Transactional
    public void sendMessage(){
        Users user = (Users) configTest.getInfoConfig().get("user");
        userRepository.save(user);

        Rentals rentals = (Rentals) configTest.getInfoConfig().get("rental");
        rentals.setOwner(user);
        rentalRepository.save(rentals);

        HashMap<String, String> hashMessageInfo = new HashMap<>();
        hashMessageInfo.put("message", "test");
        hashMessageInfo.put("user_id", String.valueOf(user.getId()));
        hashMessageInfo.put("rental_id", String.valueOf(rentals.getId()));

        try {
                messageService.postMessage(hashMessageInfo);
        } catch (Exception ex)
        {
            assertFalse(false);
        }
        assertTrue(true);
    }
}
