package com.example.bontaniq.repository;

import com.example.bontaniq.model.CareType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * A Spring Data JPA repository for managing CareType entities.<br>
 * It extends JpaRepository, which provides methods for basic CRUD operations.
 */
@Repository
public interface CareTypeRepository extends JpaRepository<CareType, Long> {
    /**
     * Searches for a CareType based on its type and the associated plant id.
     *
     * @param type The type of care.
     * @param plantId The id of the associated plant.
     * @return An Optional containing the CareType if found; <br>
     *         Empty otherwise.
     */
    Optional<CareType> findByTypeAndPlantId(String type, Long plantId);

    /**
     * Retrieves all CareTypes associated with a plant id.
     *
     * @param plantId The id of the associated plant.
     * @return A list of CareTypes related to the given plant id.
     */
    List<CareType> findByPlantId(Long plantId);
}
