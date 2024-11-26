package com.example.appwww.Services;

import com.example.appwww.Dtos.CreateVisitRequestDto;
import com.example.appwww.Models.Entities.DoctorEntity;
import com.example.appwww.Models.Entities.PatientBookEntity;
import com.example.appwww.Models.Entities.PatientEntity;
import com.example.appwww.Models.Entities.VisitEntity;
import com.example.appwww.Repositories.DoctorRepository;
import com.example.appwww.Repositories.PatientRepository;
import com.example.appwww.Repositories.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VisitServiceImpl {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public VisitServiceImpl(VisitRepository visitRepository,
                            PatientRepository patientRepository,
                            DoctorRepository doctorRepository) {
        this.visitRepository = visitRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public void createVisit(Long userId, CreateVisitRequestDto createVisitRequestDto) {
        PatientEntity patient = patientRepository.findById(createVisitRequestDto.getPatientId()).orElseThrow(
                () -> new RuntimeException("Patient not found")
        );
        DoctorEntity doctor = doctorRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Doctor not found")
        );
        if(patient.getPatientBook() == null)
            throw new RuntimeException("Patient does not have patient book yet. Please create it first.");
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitDate(LocalDate.now());
        visitEntity.setDescription(createVisitRequestDto.getDescription());
        visitEntity.setDoctor(doctor);
        visitEntity.setPatientBook(patient.getPatientBook());
        patient.getPatientBook().getVisits().add(visitEntity);
        visitRepository.save(visitEntity);
    }
}
