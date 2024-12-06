package com.pmarko09.medical_clinic_proxy.controller;

import com.pmarko09.medical_clinic_proxy.model.dto.AvailableVisitDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/available")
    public List<AvailableVisitDto> getAvailableAppointments(
            @RequestParam String specialization,
            @RequestParam LocalDate date) {
        return visitService.getAvailableAppointments(specialization, date);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<VisitDto> getPatientVisits(
            @RequestParam Long patientId) {
        return visitService.getPatientVisits(patientId);
    }
}