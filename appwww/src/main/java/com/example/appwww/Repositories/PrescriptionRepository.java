package com.example.appwww.Repositories;

import com.example.appwww.Models.Entities.PrescriptionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long> {
    Page<PrescriptionEntity> findAll(Specification<PrescriptionEntity> spec, Pageable pageable);
}
