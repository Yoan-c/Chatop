package com.example.rentals.repository;

import com.example.rentals.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
    Boolean existsByEmail(String email);
}
