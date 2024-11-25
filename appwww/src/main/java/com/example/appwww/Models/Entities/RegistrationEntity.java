package com.example.appwww.Models.Entities;

import com.example.appwww.Models.BaseEntityAudit;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "registrations", schema = "app")
public class RegistrationEntity extends BaseEntityAudit {
    private LocalDate visitDate;
    private LocalTime visitTime;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;
}
