package com.example.bontaniq.repository;

import com.example.bontaniq.model.CareTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareTrackRepository extends JpaRepository<CareTrack, Long> { //Entity, id data type
}
