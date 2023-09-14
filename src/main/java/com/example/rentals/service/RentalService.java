package com.example.rentals.service;

import com.example.rentals.entity.Rentals;
import com.example.rentals.entity.Users;
import com.example.rentals.entityDto.RentalDto;
import com.example.rentals.error.ApiCustomError;
import com.example.rentals.repository.RentalRepository;
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
    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final DocumentUtils documentUtils;
    private final ModelMapperUtils modelMapperUtils;

    public RentalService(RentalRepository rr, UserService us, DocumentUtils du, ModelMapperUtils mmu){
        this.rentalRepository = rr;
        this.userService = us;
        this.documentUtils = du;
        this.modelMapperUtils = mmu;
    }


    @Transactional
    public void createRental(HashMap<String, String> rental, MultipartFile picture) throws Exception {
        Rentals newRentals = createRentalWithData(rental);
            String pathPicture = documentUtils.uploadUserRentalPicture(picture);
            if (pathPicture != null)
                newRentals.setPicture(pathPicture);
            rentalRepository.save(newRentals);
    }

    public Rentals createRentalWithData(HashMap<String, String> rental){
        if (rental == null)
            return null;
        Users user = userService.getCurrentUser();
        Rentals newRentals = new Rentals();

        try {
            if (rental.containsKey("surface"))
                newRentals.setSurface(Float.parseFloat(rental.get("surface")));
            if (rental.containsKey("price"))
                newRentals.setPrice(Float.parseFloat(rental.get("price")));
            if (rental.containsKey("description"))
                newRentals.setDescription(rental.get("description"));
            newRentals.setName(rental.getOrDefault("name", "default name"));
            newRentals.setOwner(user);
        }
        catch (Exception ex){
            log.error("[createRental] createRentalWithData " + ex);
            throw new ApiCustomError("error", HttpStatus.BAD_REQUEST);
        }
        return newRentals;
    }

    public void putInfoRental(int id, HashMap<String, String> rental) {
        Rentals oldRental = rentalRepository.findById(id).orElseThrow();
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
