package com.example.rentals.utils;

import com.example.rentals.entity.Rentals;
import com.example.rentals.entityDto.RentalDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ModelMapperUtils {
    private final ModelMapper modelMapper;

    public ModelMapperUtils(ModelMapper mm){
        this.modelMapper = mm;
    }

    public RentalDto converRentalToRentalDTO(Rentals rentals){
        int userId = rentals.getOwner().getId();

        RentalDto rentalDto = new RentalDto();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Converter<Rentals, Integer> userIdToStringConverter = ctx -> ctx.getSource() == null ? 0 : userId;
        modelMapper.typeMap(Rentals.class, RentalDto.class)
                .addMappings(mapper -> mapper
                        .using(userIdToStringConverter).map(Rentals::getOwner, RentalDto::setOwnerId));
        modelMapper.map(rentals, rentalDto);
        return rentalDto;
    }

    public Rentals mapHashMapToRentals(HashMap<String, String> rentalMap, Rentals newRental){

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        PropertyMap<HashMap<String, String>, Rentals> propertyMap = new PropertyMap<>() {
            protected void configure() {
                skip().setOwner(null);
            }
        };
        modelMapper.addMappings(propertyMap);
        modelMapper.map(rentalMap, newRental);
        return newRental;
    }

    public List<RentalDto> convertLstRentalToLstRentalDTO(List<Rentals> lstRental) {
        List<RentalDto> lstRentalDto = new ArrayList<>();
        for (Rentals rental : lstRental) {
            lstRentalDto.add(this.converRentalToRentalDTO(rental));
        }
        return lstRentalDto;
    }
}
