package com.pmarko09.medical_clinic_proxy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {

    private Long id;
    private LocalDateTime appointmentStartTime;
    private LocalDateTime appointmentFinishTime;
    private Long doctorId;
    private Long patientId;
}
