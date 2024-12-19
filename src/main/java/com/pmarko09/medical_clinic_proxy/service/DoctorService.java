package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
import com.pmarko09.medical_clinic_proxy.model.dto.DoctorDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {

    private final MedicalClinicProxyClient medicalClinicProxyClient;

    public List<VisitDto> getAvailableVisitsForDoctor(Long doctorId) {
        log.info("Fetching available visits for doctor ID: {}", doctorId);
        return medicalClinicProxyClient.getAvailableVisitsForDoctor(doctorId);
    }

    public List<VisitDto> getAllVisitForDoctor(Long doctorId) {
        log.info("Fetching all visits for doctor ID: {}", doctorId);
        return medicalClinicProxyClient.getAllVisitsForDoctor(doctorId);
    }

    public void cancelVisitForDoctor(Long doctorId, Long visitId) {
        log.info("Cancelling visit ID: {} for doctor ID: {}", visitId, doctorId);
        medicalClinicProxyClient.cancelVisitForDoctor(doctorId, visitId);
    }

    public List<DoctorDto> getDoctorsWithSpecialization(String specialization) {
        log.info("Fetching all doctors with specialization={}", specialization);
        return medicalClinicProxyClient.getDoctorsWithSpecialization(specialization);
    }
}