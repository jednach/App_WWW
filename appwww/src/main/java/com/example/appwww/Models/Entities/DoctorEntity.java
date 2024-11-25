package com.example.appwww.Models.Entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctors", schema = "app")
public class DoctorEntity extends UserEntity {
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "doctor")
    private List<PrescriptionEntity> prescriptions = new ArrayList<>();
    @OneToMany(mappedBy = "doctor")
    private List<RegistrationEntity> registrations = new ArrayList<>();
    @OneToMany(mappedBy = "doctor")
    private List<VisitEntity> visits = new ArrayList<>();
    @OneToMany(mappedBy = "doctor")
    private List<WorkingHourEntity> workingHours = new ArrayList<>();
}
