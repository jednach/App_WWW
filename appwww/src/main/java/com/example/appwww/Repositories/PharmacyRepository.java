package com.example.appwww.Repositories;

import com.example.appwww.Models.Entities.PharmacyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface PharmacyRepository extends JpaRepository<PharmacyEntity, Long> {
}
