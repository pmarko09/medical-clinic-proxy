package com.pmarko09.medical_clinic_proxy.controller;

import com.pmarko09.medical_clinic_proxy.model.dto.ErrorMessageDto;
import com.pmarko09.medical_clinic_proxy.model.dto.PatientIdDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Patients", description = "Operations related to patients")
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Register patient for existing visit")
    @ApiResponse(responseCode = "201", description = "Patient successfully registered for visit",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisitDto.class)))
    @ApiResponse(responseCode = "404", description = "Patient/visit not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageDto.class)))
    @ApiResponse(responseCode = "409", description = "Visit is booked already",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageDto.class)))
    @PatchMapping("/register/{visitId}")
    public VisitDto registerForVisit(@Parameter(description = "Visit ID", required = true) @PathVariable Long visitId,
                                     @Parameter(description = "Patient ID", required = true) @RequestBody PatientIdDto patientId) {
        return patientService.registerForVisit(visitId, patientId);
    }
}