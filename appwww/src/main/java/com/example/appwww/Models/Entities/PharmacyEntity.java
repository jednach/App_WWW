package com.example.appwww.Models.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "pharmacies", schema = "app")
public class PharmacyEntity extends UserEntity {
    private String pharmacyName;
    private String pharmacyAddress;
    private String pharmacyCity;
    @OneToMany(mappedBy = "realizedByPharmacy")
    private List<PrescriptionEntity> realizedPrescriptions = new ArrayList<>();
}
