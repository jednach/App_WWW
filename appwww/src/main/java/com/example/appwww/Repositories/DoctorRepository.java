package com.example.appwww.Repositories;

import com.example.appwww.Models.Entities.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
}
