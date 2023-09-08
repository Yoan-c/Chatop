package com.example.rentals.service;

import com.example.rentals.entity.Register;
import com.example.rentals.entity.Rentals;
import com.example.rentals.entity.Users;
import com.example.rentals.repository.IRentalRepository;
import com.example.rentals.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RentalServiceTest {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private IRentalRepository rentalRepository;

    @Autowired
    private  AuthService authService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IUserRepository userRepository;


    public HashMap<String, Object> getImportantInfo() {
        HashMap<String , Object> hashInfo = new HashMap<>();
        // Register
        hashInfo.put("register", new Register("test2", "test2@gmail.com", "password"));

        //User
        Users user = new Users();
        user.setName("toto");
        hashInfo.put("user", user);

        // Rental
        Rentals rentals = new Rentals();
        rentals.setPrice(-1);
        rentals.setOwner(user);
        hashInfo.put("rental", rentals);
        return hashInfo;
    }

    @Test
    @Transactional
    public void createRentalPictureNull() throws Exception {
        Register register = (Register) this.getImportantInfo().get("register");
        authService.register(register);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(register.getEmail());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                null
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("surface", "50");
        hashMap.put("description", "test");
        hashMap.put("price", "50");
        Exception thrown = assertThrows(Exception.class, () ->
                rentalService.createRental(hashMap, null));
        assertTrue(thrown.getMessage().contains("ok"));
    }

    @Test
    @Transactional
    public void getAndUpdateRental() throws Exception {
        Users users = (Users) this.getImportantInfo().get("user");
        userRepository.save(users);
        Rentals rentals = (Rentals) this.getImportantInfo().get("rental");
        rentals.setOwner(users);
        Rentals newR = rentalRepository.save(rentals);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("surface", "50");
        hashMap.put("description", "test");
        hashMap.put("price", "50");
        rentalService.putInfoRental(newR.getId(), hashMap);
        assertEquals("test", newR.getDescription());
        assertEquals(rentalService.getRentalById(rentals.getId()).getId(), rentals.getId());
        assertFalse(rentalService.getAllRental().isEmpty());
    }
}
