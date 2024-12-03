package com.pmarko09.medical_clinic_proxy.model.entity;

import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    private String firstname;
    private String lastname;
    private String email;
    private Specialization specialization;
    private Set<Visit> visits = new HashSet<>();
}
