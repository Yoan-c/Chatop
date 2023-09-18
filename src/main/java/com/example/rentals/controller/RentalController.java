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

    public RentalService rentalService;
    public RentalController(RentalService rs){
        this.rentalService = rs;
    }

    @Operation(
            summary = "Create rental",
            description = "Create a rental with some information (surface, price, description) and a picture." +
                    "If there is no name then a name will be given by default ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401")
    })
    @PostMapping(value = "", consumes = "multipart/form-data")
    public ResponseEntity<HashMap<String, String>> createRental(@RequestParam HashMap<String, String> rental,
                                          @RequestParam("picture") MultipartFile picture) throws Exception {
         HashMap<String, String> rentalInfo = new HashMap<>();
         rentalService.createRental(rental, picture);
        rentalInfo.put("message", "Rental created !");
         return ResponseEntity.status(HttpStatus.OK).body(rentalInfo);
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
        HashMap<String, String> rentalInfo = new HashMap<>();
        rentalInfo.put("message", "Rental updated !");
        return ResponseEntity.status(HttpStatus.OK).body(rentalInfo);
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
        HashMap<String, Object> rentalInfo = new HashMap<>();
        List<RentalDto> rentals = rentalService.getAllRental();
        rentalInfo.put("rentals", rentals);
        return ResponseEntity.status(HttpStatus.OK).body(rentalInfo);
    }
}
