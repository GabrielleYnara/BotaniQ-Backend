package com.example.bontaniq.repository;

import com.example.bontaniq.model.Garden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *  A Spring Data JPA repository for managing Garden entities.<br>
 *  It extends JpaRepository, which provides methods for basic CRUD operations.
 *  */
@Repository
public interface GardenRepository extends JpaRepository<Garden, Long> { // Entity and id data type
    Optional<Garden> findByDescriptionAndUserId(String description, Long userId);
    Optional<Garden> findByIdAndUserId(Long gardenId, Long userId);
    List<Garden> findAllByUserId(Long userId);
}
