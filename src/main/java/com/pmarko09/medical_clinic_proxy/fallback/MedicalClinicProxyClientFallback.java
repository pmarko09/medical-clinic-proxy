package com.pmarko09.medical_clinic_proxy.fallback;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
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
        throw new RuntimeException("This is fallback. Unable to register for visit.");
    }

    @Override
    public List<VisitDto> getVisits(Long patientId) {
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