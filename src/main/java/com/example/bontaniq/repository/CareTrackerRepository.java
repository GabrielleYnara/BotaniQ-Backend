package com.example.bontaniq.repository;

import com.example.bontaniq.model.CareTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareTrackerRepository extends JpaRepository<CareTracker, Long> { //Entity, id data type
}
