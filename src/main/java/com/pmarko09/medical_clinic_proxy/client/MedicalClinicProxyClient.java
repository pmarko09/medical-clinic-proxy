package com.pmarko09.medical_clinic_proxy.client;

import com.pmarko09.medical_clinic_proxy.config.FeignConfig;
import com.pmarko09.medical_clinic_proxy.fallback.MedicalClinicProxyClientFallback;
import com.pmarko09.medical_clinic_proxy.model.dto.DoctorDto;
import com.pmarko09.medical_clinic_proxy.model.dto.PatientIdDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitSpecTimeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@FeignClient(name = "medClinicClient",
        url = "${spring.cloud.openfeign.client.config.medicalClinicProxyClient.url}",
        configuration = FeignConfig.class,
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
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    @GetMapping("/appointments/doctor/{doctorId}")
    List<VisitDto> getAllVisitsForDoctor(@PathVariable Long doctorId);

    @DeleteMapping("/doctors/{doctorId}/cancel-visit/{visitId}")
    void cancelVisitForDoctor(@PathVariable Long doctorId, @PathVariable Long visitId);

    @GetMapping("/doctors/")
    List<DoctorDto> getDoctorsWithSpecialization(@RequestParam String specialization);

    @GetMapping("/appointments/doctor/timeslot")
    List<VisitSpecTimeDto> getVisitsBySpecializationTime(
            @RequestParam String specialization,
            @RequestParam LocalTime appStart,
            @RequestParam LocalTime appFinish);

    @GetMapping("/appointments/timeslot")
    List<VisitSpecTimeDto> getAvailableAppsByTimeSlot(
            @RequestParam(required = false) String specialization,
            @RequestParam LocalTime appStart,
            @RequestParam LocalTime appFinish);
}