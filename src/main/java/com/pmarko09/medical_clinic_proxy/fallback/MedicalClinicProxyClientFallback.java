package com.pmarko09.medical_clinic_proxy.fallback;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
import com.pmarko09.medical_clinic_proxy.exception.MedicalClinicServiceUnavailableException;
import com.pmarko09.medical_clinic_proxy.model.dto.PatientIdDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
public class MedicalClinicProxyClientFallback implements MedicalClinicProxyClient {

    @Override
    public VisitDto registerForVisit(Long appId, PatientIdDto patientIdDto) {
        throw new MedicalClinicServiceUnavailableException("Fallback: Unable to register for visit. Medical Clinic is unavailable.");
    }

    @Override
    public List<VisitDto> getVisits(Long patientId) {
        System.out.println("Fallback triggered for getVisits with patientId: " + patientId);
        return Collections.emptyList();
    }

    @Override
    public List<VisitDto> getAvailableVisitsForDoctor(Long doctorId) {
        return Collections.emptyList();
    }

    @Override
    public List<VisitDto> getAvailableAppointments(String specialization, LocalDate date) {
        return Collections.emptyList();
    }
}