package com.example.appwww.Models.Entities;

import com.example.appwww.Models.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visits", schema = "app")
public class VisitEntity extends BaseEntityAudit {
    private LocalDate visitDate;
    private String description;
    @ManyToOne
    @JoinColumn(name = "patient_book_id")
    private PatientBookEntity patientBook;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;
}
