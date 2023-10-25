package com.example.bontaniq.repository;

import com.example.bontaniq.model.CareType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareTypeRepository extends JpaRepository<CareType, Long> {
}
