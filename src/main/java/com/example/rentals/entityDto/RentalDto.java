package com.example.rentals.entityDto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("owner_id")
    private int ownerId;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
}
