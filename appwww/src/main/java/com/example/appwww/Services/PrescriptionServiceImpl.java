package com.example.appwww.Services;

import com.example.appwww.Dtos.*;
import com.example.appwww.Models.Entities.*;
import com.example.appwww.Repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrescriptionServiceImpl {

    private final PrescriptionRepository prescriptionRepository;
    private final MedicineRepository medicineRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PharmacyRepository pharmacyRepository;

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, MedicineRepository medicineRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, PharmacyRepository pharmacyRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.medicineRepository = medicineRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.pharmacyRepository = pharmacyRepository;
    }


    public GetPrescriptionDetailsResponseDto getPrescription(Long userId, Long prescriptionId) {
        PrescriptionEntity prescription = prescriptionRepository.findById(prescriptionId).orElseThrow(
                () -> new EntityNotFoundException("Prescription not found")
        );
        if(patientRepository.existsById(userId) && !prescription.getPatient().getId().equals(userId))
            throw new RuntimeException("This prescription is not yours.");
        List<GetMedicineResponseDto> medicines = new ArrayList<>();
        for (MedicineEntity medicine : prescription.getMedicines()) {
            medicines.add(new GetMedicineResponseDto(
                    medicine.getId(),
                    medicine.getMedicineName(),
                    medicine.getMedicineType(),
                    medicine.getMedicineDescription()
            ));
        }
        return new GetPrescriptionDetailsResponseDto(
                prescription.getId(),
                prescription.getPatient().getId(),
                prescription.getDoctor().getId(),
                prescription.isRealized(),
                prescription.getRealizedByPharmacy() == null ? null : prescription.getRealizedByPharmacy().getId(),
                medicines
        );
    }

    public List<GetPrescriptionLikeResponseDto> getPrescriptionLike(
            Long userId, GetPrescriptionLikeRequestDto getPrescriptionLikeRequestDto) {
        Long doctorId = getPrescriptionLikeRequestDto.getDoctorId();
        Long patientId = !userId.equals(-1L) && patientRepository.existsById(userId) ?
                userId : getPrescriptionLikeRequestDto.getPatientId();
        Long realizedByPharmacyId = getPrescriptionLikeRequestDto.getRealizedByPharmacyId();
        Boolean realized = getPrescriptionLikeRequestDto.getRealized();
        boolean desc = getPrescriptionLikeRequestDto.isDesc();
        int pageNumber = getPrescriptionLikeRequestDto.getPageNumber();
        if (doctorId != null) doctorRepository.findById(doctorId).orElseThrow(
                () -> new EntityNotFoundException("Doctor not found")
        );
        if (patientId != null) patientRepository.findById(patientId).orElseThrow(
                () -> new EntityNotFoundException("Patient not found")
        );
        if (realizedByPharmacyId != null) pharmacyRepository.findById(realizedByPharmacyId).orElseThrow(
                () -> new EntityNotFoundException("Pharmacy not found")
        );
        Specification<PrescriptionEntity> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (doctorId != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("doctor").get("id"), doctorId));
            }
            if (patientId != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("patient").get("id"), patientId));
            }
            if (realizedByPharmacyId != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("realizedByPharmacy").get("id"), realizedByPharmacyId));
            }
            if (realized != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("realized"), realized));
            }
            if (desc) query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            else query.orderBy(criteriaBuilder.asc(root.get("createdAt")));
            return predicate;
        };
        Pageable pageable = PageRequest.of(Math.max(pageNumber, 0), 50);
        Page<PrescriptionEntity> prescriptionPage = prescriptionRepository.findAll(specification, pageable);
        List<GetPrescriptionLikeResponseDto> response = new ArrayList<>();
        for (PrescriptionEntity prescription : prescriptionPage.getContent()) {
            response.add(new GetPrescriptionLikeResponseDto(
                    prescription.getId(),
                    prescription.isRealized(),
                    prescription.getCreatedAt().toLocalDate()
            ));
        }
        return response;
    }

    @Transactional
    public void createPrescription(Long userId, CreatePrescriptionRequestDto createPrescriptionRequestDto) {
        if (createPrescriptionRequestDto.getMedicineIds().isEmpty())
            throw new RuntimeException("Must contain at least one medicine");

        DoctorEntity doctorEntity = doctorRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Doctor not found")
        );

        PatientEntity patientEntity = patientRepository.findById(createPrescriptionRequestDto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        List<MedicineEntity> medicines = medicineRepository.findAllById(createPrescriptionRequestDto.getMedicineIds());
        if (medicines.size() != createPrescriptionRequestDto.getMedicineIds().size()) {
            throw new EntityNotFoundException("Not every medicine was found");
        }
        PrescriptionEntity prescriptionEntity = new PrescriptionEntity();
        prescriptionEntity.setRealized(false);
        prescriptionEntity.setPatient(patientEntity);
        prescriptionEntity.setDoctor(doctorEntity);
        prescriptionEntity.setRealizedByPharmacy(null);
        prescriptionEntity.getMedicines().addAll(medicines);
        prescriptionRepository.save(prescriptionEntity);
    }

    public void realizePrescription(Long userId, Long prescriptionId) {
        PharmacyEntity pharmacy = pharmacyRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Pharmacy not found")
        );
        PrescriptionEntity prescription = prescriptionRepository.findById(prescriptionId).orElseThrow(
                () -> new EntityNotFoundException("Prescription not found")
        );
        if (prescription.isRealized()) throw new RuntimeException("Prescription is already realized.");
        prescription.setRealized(true);
        prescription.setRealizedByPharmacy(pharmacy);
        pharmacy.getRealizedPrescriptions().add(prescription);
        prescriptionRepository.save(prescription);
    }

    public void deletePrescription(Long userId, Long prescriptionId) {
        PrescriptionEntity prescription = prescriptionRepository.findById(prescriptionId).orElseThrow(
                () -> new EntityNotFoundException("Prescription not found")
        );
        if (!userId.equals(prescription.getDoctor().getId()) && userId.equals(-1L))
            throw new RuntimeException("You cannot delete prescription that is not yours.");
        prescriptionRepository.delete(prescription);
    }
}
