package com.example.rentals.service;

import com.example.rentals.entity.Messages;
import com.example.rentals.entity.Rentals;
import com.example.rentals.entity.Users;
import com.example.rentals.error.ApiCustomError;
import com.example.rentals.repository.MessageRepository;
import com.example.rentals.repository.RentalRepository;
import com.example.rentals.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Slf4j
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public MessageService(MessageRepository mr, UserRepository ur, RentalRepository rr){
        this.messageRepository = mr;
        this.userRepository = ur;
        this.rentalRepository = rr;
    }

    @Transactional
    public void postMessage(HashMap<String, String> hashMsgInfo) {
        Messages newMessage = new Messages();
        try {
            Users user = userRepository.findById(Integer.parseInt(hashMsgInfo.get("user_id"))).orElseThrow();
            Rentals rentals = rentalRepository.findById(Integer.parseInt(hashMsgInfo.get("rental_id"))).orElseThrow();
            newMessage.setUser(user);
            newMessage.setMessage(hashMsgInfo.get("message"));
            newMessage.setRental(rentals);
            messageRepository.save(newMessage);
        } catch (Exception ex){
            throw new ApiCustomError("error", HttpStatus.BAD_REQUEST);
        }
    }
}
