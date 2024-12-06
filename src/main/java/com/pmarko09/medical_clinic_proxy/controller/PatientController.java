package com.pmarko09.medical_clinic_proxy.controller;

import com.pmarko09.medical_clinic_proxy.model.dto.PatientIdDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/register/{visitId}")
    public VisitDto registerForVisit(@PathVariable Long visitId, @RequestBody PatientIdDto patientId) {
        return patientService.registerForVisit(visitId, patientId);
    }
}
