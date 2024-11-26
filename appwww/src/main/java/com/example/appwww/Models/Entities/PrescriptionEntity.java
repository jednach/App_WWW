package com.example.appwww.Models.Entities;

import com.example.appwww.Models.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prescriptions", schema = "app")
public class PrescriptionEntity extends BaseEntityAudit {
    private boolean realized;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;
    @ManyToOne
    @JoinColumn(name = "realized_by_pharmacy_id")
    private PharmacyEntity realizedByPharmacy;
    @ManyToMany
    @JoinTable(
            name = "prescription_medicine",
            schema = "app",
            joinColumns = @JoinColumn(name = "prescription_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    private List<MedicineEntity> medicines = new ArrayList<>();
}
