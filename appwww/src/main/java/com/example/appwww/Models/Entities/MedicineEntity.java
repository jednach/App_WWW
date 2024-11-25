package com.example.appwww.Models.Entities;

import com.example.appwww.Models.BaseEntityAudit;
import com.example.appwww.Models.Enums.MedicineType;
import com.example.appwww.Models.Enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medicines", schema = "app")
public class MedicineEntity extends BaseEntityAudit {
    private String medicineName;
    @Enumerated(value = EnumType.STRING)
    private MedicineType medicineType;
    private String medicineDescription;
    @ManyToMany(mappedBy = "medicines")
    @JsonIgnore
    private List<PrescriptionEntity> prescriptions = new ArrayList<>();
}
