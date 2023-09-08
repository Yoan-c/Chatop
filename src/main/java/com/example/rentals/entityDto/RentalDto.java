package com.example.rentals.entityDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {
    private int id;
    private String name;
    private float surface;
    private float price;
    private String picture;
    private String description;
    private int owner_id;
    private Date created_at;
    private Date updated_at;
}
