package com.example.appwww.Repositories;

import com.example.appwww.Models.Entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
}
