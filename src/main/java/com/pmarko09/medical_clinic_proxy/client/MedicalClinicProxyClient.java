package com.pmarko09.medical_clinic_proxy.client;

import com.pmarko09.medical_clinic_proxy.configuration.FeignRetryConfig;
import com.pmarko09.medical_clinic_proxy.fallback.MedicalClinicProxyClientFallback;
import com.pmarko09.medical_clinic_proxy.model.dto.PatientIdDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "medClinicClient",
        url = "${spring.cloud.openfeign.client.config.medicalClinicProxyClient.url}",
        configuration = FeignRetryConfig.class,
        fallback = MedicalClinicProxyClientFallback.class)
public interface MedicalClinicProxyClient {

    @PatchMapping("/appointments/{appId}")
    VisitDto registerForVisit(
            @PathVariable Long appId,
            @RequestBody PatientIdDto patientIdDto
    );

    @GetMapping("/appointments")
    List<VisitDto> getVisits(
            @RequestParam("patientId") Long patientId
    );

    @GetMapping("/appointments/doctor/{doctorId}/available")
    List<VisitDto> getAvailableVisitsForDoctor(@PathVariable Long doctorId);

    @GetMapping("/appointments/available")
    List<VisitDto> getAvailableAppointments(
            @RequestParam("specialization") String specialization,
            @RequestParam("date") LocalDate date);
}
