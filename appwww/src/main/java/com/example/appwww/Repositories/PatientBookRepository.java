package com.example.appwww.Repositories;

import com.example.appwww.Models.Entities.PatientBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientBookRepository extends JpaRepository<PatientBookEntity, Long> {
}
