package com.example.rentals.service;

import com.example.rentals.entity.Rentals;
import com.example.rentals.entity.Users;
import com.example.rentals.entityDto.RentalDto;
import com.example.rentals.error.ApiCustomError;
import com.example.rentals.repository.IRentalRepository;
import com.example.rentals.utils.DocumentUtils;
import com.example.rentals.utils.ModelMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class RentalService {

    @Autowired
    private IRentalRepository rentalRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentUtils documentUtils;

    @Autowired
    private ModelMapperUtils modelMapperUtils;


    @Transactional
    public void createRental(HashMap<String, String> hashRental, MultipartFile picture) throws Exception {
        Rentals rentals = createRentalWithData(hashRental);
            String pathPicture = documentUtils.uploadUserRentalPicture(picture);
            if (pathPicture != null)
                rentals.setPicture(pathPicture.substring(pathPicture.lastIndexOf("/") + 1));
            rentalRepository.save(rentals);
    }

    public Rentals createRentalWithData(HashMap<String, String> hashRental){
        if (hashRental == null)
            return null;
        Users user = userService.getCurrentUser();
        Rentals rentals = new Rentals();

        try {
            if (hashRental.containsKey("surface"))
                rentals.setSurface(Float.parseFloat(hashRental.get("surface")));
            if (hashRental.containsKey("price"))
                rentals.setPrice(Float.parseFloat(hashRental.get("price")));
            if (hashRental.containsKey("description"))
                rentals.setDescription(hashRental.get("description"));
            rentals.setName(hashRental.getOrDefault("name", "default name"));
            rentals.setOwner(user);
        }
        catch (Exception ex){
            log.error("[createRental] createRentalWithData " + ex);
            throw new ApiCustomError("error", HttpStatus.BAD_REQUEST);
        }
        return rentals;
    }

    public void putInfoRental(int id, HashMap<String, String> rental) {
        Rentals oldRental = rentalRepository.getById(id);
        Rentals newRental = modelMapperUtils.mapHashMapToRentals(rental, oldRental);
        rentalRepository.save(newRental);
    }

    public RentalDto getRentalById(int id) {
        Rentals rentals = rentalRepository.findById(id)
                .orElseThrow();
        return modelMapperUtils.converRentalToRentalDTO(rentals);
    }

    public List<RentalDto> getAllRental() {
        List<Rentals> lstRental = rentalRepository.findAll();
        return modelMapperUtils.convertLstRentalToLstRentalDTO(lstRental);
    }
}
