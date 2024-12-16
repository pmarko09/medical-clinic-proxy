package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
import com.pmarko09.medical_clinic_proxy.mapper.VisitMapper;
import com.pmarko09.medical_clinic_proxy.model.dto.AvailableVisitDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitService {

    private final MedicalClinicProxyClient medicalClinicProxyClient;
    private final VisitMapper visitMapper;

    public List<AvailableVisitDto> getAvailableAppointments(String specialization, LocalDate date) {
        List<VisitDto> availableVisits = medicalClinicProxyClient.getAvailableAppointments(specialization, date);
        log.info("Fetching available visits for specialization: {} and date: {}", specialization, date);
        return availableVisits.stream()
                .map(visitDto -> visitMapper.toAvailableVisitDto(visitDto, specialization))
                .toList();
    }

    public List<VisitDto> getPatientVisits(Long patientId) {
        log.info("Fetching visits for patient ID: {}", patientId);
        return medicalClinicProxyClient.getVisits(patientId);
    }
}