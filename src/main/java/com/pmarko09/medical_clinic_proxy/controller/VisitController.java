package com.pmarko09.medical_clinic_proxy.controller;

import com.pmarko09.medical_clinic_proxy.model.dto.AvailableVisitDto;
import com.pmarko09.medical_clinic_proxy.model.dto.ErrorMessageDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.service.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Appointments", description = "Operations related to appointments and visits")
@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @Operation(summary = "Get available appointments for a given specialization and date",
            description = "Fetches a list of available appointments for a specific specialization on a given date.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved available appointments",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AvailableVisitDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid date or specialization",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageDto.class)))
    @GetMapping("/available")
    public List<AvailableVisitDto> getAvailableAppointments(
            @Parameter(description = "Specialization of the doctor", required = true)
            @RequestParam String specialization,
            @Parameter(description = "Date for available appointments", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return visitService.getAvailableAppointments(specialization, date);
    }

    @Operation(summary = "Get all visits for a patient",
            description = "Fetches all visits for the patient specified by patientId.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved patient's visits",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisitDto.class)))
    @ApiResponse(responseCode = "404", description = "Patient not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageDto.class)))
    @GetMapping
    public List<VisitDto> getPatientVisits(
            @RequestParam Long patientId) {
        return visitService.getPatientVisits(patientId);
    }
}