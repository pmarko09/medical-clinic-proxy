package com.pmarko09.medical_clinic_proxy.controller;

import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{doctorId}/available-visits")
    public List<VisitDto> getAvailableVisitsForDoctor(@PathVariable Long doctorId) {
        return doctorService.getAvailableVisitsForDoctor(doctorId);
    }
}
