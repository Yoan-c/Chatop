package com.example.rentals.utils;

import com.example.rentals.entity.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ConfigTest {

    public HashMap<String, Object> getInfoConfig() {
        HashMap<String , Object> hashInfo = new HashMap<>();
        // Register
        hashInfo.put("register", new Register("testRegister", "testRegister@gmail.com", "password"));

        //Login
        hashInfo.put("login", new LogIn("testRegister", "testRegister"));
        //User
        Users user = new Users();
        user.setName("testRegister");
        hashInfo.put("user", user);

        // Rental
        Rentals rentals = new Rentals();
        rentals.setPrice(-1);
        rentals.setOwner(user);
        hashInfo.put("rental", rentals);

        //Message
        Messages messages = new Messages();
        messages.setMessage("Message de test");
        hashInfo.put("message", messages);
        return hashInfo;
    }
}
