package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
import com.pmarko09.medical_clinic_proxy.mapper.VisitMapper;
import com.pmarko09.medical_clinic_proxy.model.dto.AvailableVisitDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitSpecTimeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public List<VisitSpecTimeDto> getVisitsBySpecializationAndTimeslot(String specialization, LocalTime appStart,
                                                                       LocalTime appFinish) {
        log.info("Fetching visits by selected specialization: {} and during mentioned time slot between: {} and {}",
                specialization, appStart, appFinish);
        return medicalClinicProxyClient.getVisitsBySpecializationTime(specialization, appStart, appFinish);
    }

    public List<VisitSpecTimeDto> getAvailableAppsByTimeSlot(String specialization, LocalTime appStart,
                                                             LocalTime appFinish) {
        log.info("Fetching visits by during mentioned time slot between: {} and {}", appStart, appFinish);
        return medicalClinicProxyClient.getAvailableAppsByTimeSlot(specialization, appStart, appFinish);
    }
}