package com.example.appwww.Services;

import com.example.appwww.Dtos.GetPatientBookResponseDto;
import com.example.appwww.Dtos.GetVisitInfoResponseDto;
import com.example.appwww.Models.Entities.PatientBookEntity;
import com.example.appwww.Models.Entities.PatientEntity;
import com.example.appwww.Models.Entities.VisitEntity;
import com.example.appwww.Dtos.CreatePatientBookRequestDto;
import com.example.appwww.Repositories.PatientBookRepository;
import com.example.appwww.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientBookServiceImpl {

    private final PatientBookRepository patientBookRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public PatientBookServiceImpl(PatientBookRepository patientBookRepository, PatientRepository patientRepository) {
        this.patientBookRepository = patientBookRepository;
        this.patientRepository = patientRepository;
    }

    public GetPatientBookResponseDto getPatientBook(Long userId, Long patientId) {
        PatientEntity patientEntity = patientRepository.findById(patientId).orElseThrow(
                () -> new RuntimeException("Patient not found")
        );
        if (patientRepository.existsById(userId) && !userId.equals(patientId))
            throw new RuntimeException("You are not supposed to see this data.");
        PatientBookEntity patientBookEntity = patientEntity.getPatientBook();
        if (patientBookEntity == null)
            throw new RuntimeException("Patient does not have patient book yet. Please create it first.");
        List<GetVisitInfoResponseDto> visits = new ArrayList<>();
        for (VisitEntity visitEntity : patientBookEntity.getVisits()) {
            visits.add(new GetVisitInfoResponseDto(
                    visitEntity.getId(),
                    visitEntity.getDoctor().getId(),
                    visitEntity.getVisitDate(),
                    visitEntity.getDescription()
            ));
        }
        return new GetPatientBookResponseDto(
                patientBookEntity.getId(),
                patientEntity.getFirstName(),
                patientEntity.getLastName(),
                patientEntity.getPeselNumber(),
                patientEntity.getBirthDate(),
                patientEntity.isGender(),
                patientBookEntity.getPatientInfo(),
                visits
        );

    }

    public void createPatientBook(Long patientId, CreatePatientBookRequestDto createPatientBookRequestDto) {
        PatientEntity patientEntity = patientRepository.findById(patientId).orElseThrow(
                () -> new RuntimeException("Patient not found")
        );
        if (patientEntity.getPatientBook() != null) throw new RuntimeException("Patient book already exists");
        PatientBookEntity patientBook = new PatientBookEntity();
        patientBook.setPatient(patientEntity);
        patientBook.setPatientInfo(createPatientBookRequestDto.getPatientInfo());
        patientBookRepository.save(patientBook);
    }
}
