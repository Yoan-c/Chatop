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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="owner_id",  nullable = false)
    private Users owner;
    private Date created_at;
    private Date updated_at;

    @PrePersist
    public void onCreate(){
        this.created_at = new Date();
        this.updated_at = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        this.updated_at = new Date();
    }
}
