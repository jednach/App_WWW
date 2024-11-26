package com.example.appwww.Repositories;

import com.example.appwww.Models.Entities.MedicineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<MedicineEntity, Long> {
    Page<MedicineEntity> findAll(Specification<MedicineEntity> spec, Pageable pageable);
}
