package com.example.bontaniq.repository;

import com.example.bontaniq.model.CareTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareTrackRepository extends JpaRepository<CareTrack, Long> { //Entity, id data type
    @Query("SELECT ct.date, ct.done, c.type\n" +
            "FROM CareTrack ct\n" +
            "JOIN CareType c ON ct.careType.id = c.id\n" +
            "JOIN Plant p ON c.plant.id = p.id\n" +
            "WHERE p.id = :plantId")
    List<Object[]> findCareTrackDetailsByPlantId(@Param("plantId") Long plantId);
}

