package com.pmarko09.medical_clinic_proxy.model.entity;

import com.pmarko09.medical_clinic_proxy.model.dto.PatientDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Visit {

    private Long id;
    private LocalDateTime visitStartTime;
    private LocalDateTime visitEndTime;
    private PatientDto patient;
}
