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
    /**
     * Searches garden by its description and user id.
     * @param description The garden's description.
     * @param userId The user's id.
     * @return The Garden if found.<br>
     *         Empty Optional it not.
     */
    Optional<Garden> findByDescriptionAndUserId(String description, Long userId);

    /**
     * Searches garden by its id and user id.
     * @param gardenId The garden's id.
     * @param userId The user's id.
     * @return The Garden if found.<br>
     *         Empty Optional it not.
     */
    Optional<Garden> findByIdAndUserId(Long gardenId, Long userId);

    /**
     * Searches all gardens associated to a user.
     * @param userId The user's id.
     * @return A list of gardens if found.
     *         Empty List it not.
     */
    List<Garden> findAllByUserId(Long userId);
}
