package com.example.bontaniq.repository;

import com.example.bontaniq.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *  A Spring Data JPA repository for managing Profile entities.<br>
 *  It extends JpaRepository, which provides methods for basic CRUD operations.
 *  */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> { //Entity, id data type
    Optional<Profile> findById(Long profileId);
}
