package com.pmarko09.medical_clinic_proxy.model.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
    private Set<Long> hospitalsIds;
    private Set<Long> appointmentIds;
}
