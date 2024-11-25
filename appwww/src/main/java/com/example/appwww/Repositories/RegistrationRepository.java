package com.example.appwww.Repositories;

import com.example.appwww.Models.Entities.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Long> {
    List<RegistrationEntity> findByDoctorIdAndVisitDateOrderByVisitTimeAsc(Long doctorId, LocalDate date);
    List<RegistrationEntity> findByPatientId(Long patientId);
}
