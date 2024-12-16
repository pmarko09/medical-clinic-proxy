package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
import com.pmarko09.medical_clinic_proxy.model.dto.PatientIdDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final MedicalClinicProxyClient medicalClinicProxyClient;

    public VisitDto registerForVisit(Long visitId, PatientIdDto patientId) {
        log.info("Registering patient (ID: {}) for visit ID: {}", patientId.getId(), visitId);
        return medicalClinicProxyClient.registerForVisit(visitId, patientId);
    }
}