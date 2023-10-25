package com.example.bontaniq.repository;

import com.example.bontaniq.model.CareType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareTypeRepository extends JpaRepository<CareType, Long> {
    Optional<CareType> findByTypeAndPlantId(String type, Long plantId);
}
