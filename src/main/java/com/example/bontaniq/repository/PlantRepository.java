package com.example.bontaniq.repository;

import com.example.bontaniq.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *  A Spring Data JPA repository for managing Plant entities.<br>
 *  It extends JpaRepository, which provides methods for basic CRUD operations.
 *  */
@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> { //Entity, id data type
}
