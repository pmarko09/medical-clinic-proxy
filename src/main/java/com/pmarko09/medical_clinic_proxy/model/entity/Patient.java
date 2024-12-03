package com.pmarko09.medical_clinic_proxy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    private String firstname;
    private String lastname;
    private String email;
    private LocalDate birthday;
    private List<Visit> visits;
}
