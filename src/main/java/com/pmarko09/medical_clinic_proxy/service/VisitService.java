package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
import com.pmarko09.medical_clinic_proxy.model.dto.AvailableVisitDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final MedicalClinicProxyClient medicalClinicProxyClient;

    public List<AvailableVisitDto> getAvailableAppointments(String specialization, LocalDate date) {
        List<VisitDto> availableVisits = medicalClinicProxyClient.getAvailableAppointments(specialization, date);

        return availableVisits.stream()
                .map(visitDto -> new AvailableVisitDto(
                        visitDto.getAppointmentStartTime(),
                        visitDto.getAppointmentFinishTime(),
                        specialization
                ))
                .toList();
    }

    public List<VisitDto> getPatientVisits(Long patientId) {
        return medicalClinicProxyClient.getVisits(patientId);
    }
}