package com.example.appwww.Services;

import com.example.appwww.Dtos.GetPatientRequestDto;
import com.example.appwww.Dtos.GetPatientResponseDto;
import com.example.appwww.Models.Entities.MedicineEntity;
import com.example.appwww.Models.Entities.PatientEntity;
import com.example.appwww.Repositories.PatientRepository;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientServiceImpl {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<GetPatientResponseDto> getPatients(GetPatientRequestDto getPatientRequestDto) {
        String firstName = getPatientRequestDto.getFirstName();
        String lastName = getPatientRequestDto.getLastName();
        String peselNumber = getPatientRequestDto.getPeselNumber();
        LocalDate birthDate = getPatientRequestDto.getBirthDate();
        Boolean gender = getPatientRequestDto.getGender();
        int pageNumber = getPatientRequestDto.getPageNumber();
        Specification<PatientEntity> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(firstName)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("firstName")), firstName.toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(lastName)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("lastName")), lastName.toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(peselNumber)) {
                predicate = criteriaBuilder.and(
                        predicate, criteriaBuilder.equal(root.get("email"), peselNumber + "%"));
            }
            if (birthDate != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("birthDate"), birthDate));
            }
            if(gender != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("gender"), gender));
            }
            return predicate;
        };
        Pageable pageable = PageRequest.of(Math.max(pageNumber, 0), 30);
        Page<PatientEntity> page = patientRepository.findAll(specification, pageable);
        List<GetPatientResponseDto> response = new ArrayList<>();
        for(PatientEntity patientEntity : page.getContent()) {
            response.add(new GetPatientResponseDto(
                    patientEntity.getId(),
                    patientEntity.getPatientBook() == null ? null : patientEntity.getPatientBook().getId(),
                    patientEntity.getFirstName(),
                    patientEntity.getLastName(),
                    patientEntity.getBirthDate(),
                    patientEntity.isGender(),
                    patientEntity.getPhoneNumber(),
                    patientEntity.getPeselNumber()
            ));
        }
        return response;
    }
}
