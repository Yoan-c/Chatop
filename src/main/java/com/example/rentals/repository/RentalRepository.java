package com.example.rentals.repository;

import com.example.rentals.entity.Rentals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rentals, Integer> {
}
