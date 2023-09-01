package com.example.rentals.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Rentals {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private float surface;
    private float price;
    private String picture;
    @Column(length = 2000)
    private String description;
    @ManyToOne
    @JoinColumn(name ="owner_id",  nullable = false)
    private Users owner;
    private Date created_at;
    private Date updated_at;
}
