package com.pmarko09.medical_clinic_proxy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisitSpecTimeDto {

    private Long visitId;
    private String specialization;
    private LocalDate visitDate;
    private LocalTime visitStart;
    private LocalTime visitFinish;
}
