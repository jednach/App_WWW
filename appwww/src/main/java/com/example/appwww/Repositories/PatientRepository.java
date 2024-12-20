package com.example.appwww.Repositories;

import com.example.appwww.Models.Entities.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    Page<PatientEntity> findAll(Specification<PatientEntity> specification, Pageable pageable);
}
