package com.example.appwww.Services;

import com.example.appwww.Dtos.GetMedicineRequestDto;
import com.example.appwww.Dtos.GetMedicineResponseDto;
import com.example.appwww.Models.Entities.MedicineEntity;
import com.example.appwww.Models.Enums.MedicineType;
import com.example.appwww.Repositories.MedicineRepository;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MedicineServiceImpl {

    private final MedicineRepository medicineRepository;

    @Autowired
    public MedicineServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Set<MedicineType> getAllMedicineTypes() {
        List<MedicineEntity> medicineEntities = medicineRepository.findAll();
        Set<MedicineType> medicineTypes = new HashSet<>();
        for (MedicineEntity medicineEntity : medicineEntities) medicineTypes.add(medicineEntity.getMedicineType());
        return medicineTypes;
    }

    public List<GetMedicineResponseDto> getMedicinesLike(GetMedicineRequestDto getMedicineRequestDto) {
        String medicineName = getMedicineRequestDto.getMedicineName();
        MedicineType medicineType = getMedicineRequestDto.getMedicineType();
        int pageNumber = getMedicineRequestDto.getPageNumber();
        Specification<MedicineEntity> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(medicineName)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("medicineName")), medicineName.toLowerCase() + "%"));
            }
            if (medicineType != null) {
                predicate = criteriaBuilder.and(
                        predicate, criteriaBuilder.equal(root.get("medicineType"), medicineType));
            }
            return predicate;
        };
        Pageable pageable = PageRequest.of(Math.max(pageNumber, 0), 30);
        Page<MedicineEntity> page = medicineRepository.findAll(specification, pageable);
        List<GetMedicineResponseDto> response = new ArrayList<>();
        for(MedicineEntity medicineEntity : page.getContent()) {
            response.add(new GetMedicineResponseDto(
                    medicineEntity.getId(),
                    medicineEntity.getMedicineName(),
                    medicineEntity.getMedicineType(),
                    medicineEntity.getMedicineDescription()
            ));
        }
        return response;
    }
}
