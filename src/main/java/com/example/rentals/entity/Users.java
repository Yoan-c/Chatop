package com.example.rentals.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String email;
    private String name;
    private String password;
    private Date created_at;
    private Date updated_at;

    @OneToMany(mappedBy = "owner")
    private List<Rentals> rentalsList;

}
