package com.example.appwww.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePharmacyRequestDto {
    private String email;
    private String password;
    private String phoneNumber;
    private String pharmacyName;
    private String pharmacyAddress;
    private String pharmacyCity;
}
