package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
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
        log.info("Fetching visits for doctor ID: {}", doctorId);
        return medicalClinicProxyClient.getAvailableVisitsForDoctor(doctorId);
    }
}