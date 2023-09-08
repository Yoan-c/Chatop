package com.example.rentals.controller;

import com.example.rentals.entityDto.RentalDto;
import com.example.rentals.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    public RentalService rentalService;

    @Operation(
            summary = "Create rental",
            description = "Create a rental with some information (surface, price, description) and a picture." +
                    "If there is no name then a name will be given by default ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401")
    })
    @PostMapping(value = "", consumes = "multipart/form-data")
    public ResponseEntity<HashMap> createRental(@RequestParam HashMap<String, String> hashRental,
                                          @RequestParam("picture") MultipartFile picture) throws Exception {
         HashMap<String, String> hashRentalInfo = new HashMap<>();
         rentalService.createRental(hashRental, picture);
         hashRentalInfo.put("message", "Rental created !");
         return ResponseEntity.status(HttpStatus.OK).body(hashRentalInfo);
    }

    @Operation(
            summary = "Update rental",
            description = "Update a rental information (name, surface, price, description)." +
                    "If there is no name then a name will be given by default ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<HashMap<String, String>> PutRental(@PathVariable int id,
                                                             @RequestParam HashMap<String, String> rentals) {
        rentalService.putInfoRental(id, rentals);
        HashMap<String, String> hashRentalInfo = new HashMap<>();
        hashRentalInfo.put("message", "Rental updated !");
        return ResponseEntity.status(HttpStatus.OK).body(hashRentalInfo);
    }

    @Operation(
            summary = "Update rental",
            description = "Update a rental information (name, surface, price, description)." +
                    "If there is no name then a name will be given by default ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<RentalDto> getRentalById(@PathVariable int id) {
        RentalDto rentals = rentalService.getRentalById(id);
        return ResponseEntity.status(HttpStatus.OK).body(rentals);
    }

    @Operation(
            summary = "Get all Rental",
            description = "Get a list of all rental ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401")
    })
    @GetMapping(value = "")
    public ResponseEntity<HashMap<String, Object>> getAllRental() {
        HashMap<String, Object> lstRentalInfo = new HashMap<>();
        List<RentalDto> lstRentals = rentalService.getAllRental();
        lstRentalInfo.put("rentals", lstRentals);
        return ResponseEntity.status(HttpStatus.OK).body(lstRentalInfo);
    }

}
