package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
import com.pmarko09.medical_clinic_proxy.model.dto.PatientIdDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final MedicalClinicProxyClient medicalClinicProxyClient;

    public VisitDto registerForVisit(Long visitId, PatientIdDto patientId) {
        return medicalClinicProxyClient.registerForVisit(visitId, patientId);
    }
}