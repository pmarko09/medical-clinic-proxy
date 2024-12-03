package com.pmarko09.medical_clinic_proxy.controller;

import com.pmarko09.medical_clinic_proxy.model.dto.PatientDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{email}")
    public List<VisitDto> getPatientVisits(@PathVariable String email) {
        return patientService.getPatientVisits(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/register/{visitId}/{patientId}")
    public VisitDto registerForVisit(@PathVariable Long visitId, @PathVariable Long patientId) {
        return patientService.registerForVisit(visitId, patientId);
    }
}
