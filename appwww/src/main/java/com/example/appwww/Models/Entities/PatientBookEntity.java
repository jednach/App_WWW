package com.example.appwww.Models.Entities;

import com.example.appwww.Models.BaseEntityAudit;
import jakarta.persistence.*;
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
@Table(name = "patient_books", schema = "app")
public class PatientBookEntity extends BaseEntityAudit {
    private String patientInfo;
    @OneToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;
    @OneToMany(mappedBy = "patientBook")
    private List<VisitEntity> visits = new ArrayList<>();
}
