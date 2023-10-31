package com.example.bontaniq.repository;

import com.example.bontaniq.model.CareTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A Spring Data JPA repository for managing CareTrack entities.<br>
 * It extends JpaRepository, which provides methods for basic CRUD operations.
 */
@Repository
public interface CareTrackRepository extends JpaRepository<CareTrack, Long> { //Entity, id data type
    /**
     * Retrieves care track details associated with a plant id.
     * <p>
     *     The method returns a list of objects containing the date, done status, and care type for each care track record related to the given plant id.
     * </p>
     *
     * @param plantId The id of the associated plant.
     * @return A list of objects containing care track details.
     */
    @Query("SELECT ct.date, ct.done, c.type\n" +
            "FROM CareTrack ct\n" +
            "JOIN CareType c ON ct.careType.id = c.id\n" +
            "JOIN Plant p ON c.plant.id = p.id\n" +
            "WHERE p.id = :plantId")
    List<Object[]> findCareTrackDetailsByPlantId(@Param("plantId") Long plantId);
}

