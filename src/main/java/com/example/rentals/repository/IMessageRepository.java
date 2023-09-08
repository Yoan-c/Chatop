package com.example.rentals.repository;

import com.example.rentals.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageRepository extends JpaRepository<Messages, Integer> {
}
