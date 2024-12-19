package com.pmarko09.medical_clinic_proxy.controller;

import com.pmarko09.medical_clinic_proxy.model.dto.DoctorDto;
import com.pmarko09.medical_clinic_proxy.model.dto.ErrorMessageDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Doctors", description = "Operations related to doctors and their available visits")
@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @Operation(summary = "Get available visits for a doctor",
            description = "Fetches a list of available visits for the doctor specified by doctorId.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved available visits",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisitDto.class)))
    @ApiResponse(responseCode = "404", description = "Doctor not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageDto.class)))
    @GetMapping("/{doctorId}/available-visits")
    public List<VisitDto> getAvailableVisitsForDoctor(@Parameter(description = "Doctor ID", required = true)
                                                      @PathVariable Long doctorId) {
        log.info("Endpoint called: /doctors/{}/available-visits", doctorId);
        log.info("Fetching available visits for doctor with ID: {}", doctorId);
        return doctorService.getAvailableVisitsForDoctor(doctorId);
    }

    @GetMapping("/{doctorId}")
    public List<VisitDto> getAllVisitForDoctor(@PathVariable Long doctorId) {
        log.info("Endpoint called: /doctors/{}", doctorId);
        log.info("Fetching all visit for doctor ID: {}", doctorId);
        return doctorService.getAllVisitForDoctor(doctorId);
    }

    @DeleteMapping("/{doctorId}/cancel-visit/{visitId}")
    public void cancelVisitForDoctor(@PathVariable Long doctorId, @PathVariable Long visitId) {
        log.info("Endpoint called: /doctors/{}/cancel-visit/{}", doctorId, visitId);
        log.info("Cancelling visit ID: {} for doctor ID: {}", visitId, doctorId);
        doctorService.cancelVisitForDoctor(doctorId, visitId);
    }

    @GetMapping("/")
    public List<DoctorDto> getDoctorsWithSpecialization(@RequestParam String specialization) {
        log.info("Endpoint called: /doctors/specialization={}", specialization);
        log.info("Fetching all doctors with specialization={}", specialization);
        return doctorService.getDoctorsWithSpecialization(specialization);
    }
}