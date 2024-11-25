package com.example.appwww.Models.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patients", schema = "app")
public class PatientEntity extends UserEntity {
    private String firstName;
    private String lastName;
    private boolean gender;
    private String peselNumber;
    private LocalDate birthDate;
    @OneToMany(mappedBy = "patient")
    private List<PrescriptionEntity> prescriptions = new ArrayList<>();
    @OneToMany(mappedBy = "patient")
    private List<RegistrationEntity> registrations = new ArrayList<>();
    @OneToOne(mappedBy = "patient")
    private PatientBookEntity patientBook;
}
