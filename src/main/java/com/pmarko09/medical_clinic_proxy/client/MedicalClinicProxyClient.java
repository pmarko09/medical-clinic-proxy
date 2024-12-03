package com.pmarko09.medical_clinic_proxy.client;

import com.pmarko09.medical_clinic_proxy.model.dto.DoctorDto;
import com.pmarko09.medical_clinic_proxy.model.dto.PatientDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "medClinicClient", url = "${spring.cloud.openfeign.client.config.medicalClinicProxyClient.url}")
public interface MedicalClinicProxyClient {

    @GetMapping("/patients/{email}")
    PatientDto getPatient(
            @PathVariable String email
    );

    @GetMapping("/doctors/{email}")
    DoctorDto getDoctor(
            @PathVariable String email
    );

    @PatchMapping("/appointments/{appId}/{patientId}")
    VisitDto registerForVisit(
            @PathVariable Long appId,
            @PathVariable Long patientId
    );

    @GetMapping("/appointments")
    List<VisitDto> getVisits();

    @GetMapping("/appointments/doctor/{doctorId}/available")
    List<VisitDto> getAvailableVisitsForDoctor(@PathVariable Long doctorId);
}
