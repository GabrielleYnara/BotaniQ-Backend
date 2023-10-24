package com.example.bontaniq.repository;

import com.example.bontaniq.model.Garden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  A Spring Data JPA repository for managing Garden entities.<br>
 *  It extends JpaRepository, which provides methods for basic CRUD operations.
 *  */
@Repository
public interface GardenRepository extends JpaRepository<Garden, Long> { // Entity and id data type
}
