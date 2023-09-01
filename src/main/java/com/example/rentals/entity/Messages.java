package com.example.rentals.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Rentals rental;
    @ManyToOne
    private Users user;
    @Column(length = 2000)
    private String message;
    private Date created_at;
    private Date updated_at;
}
