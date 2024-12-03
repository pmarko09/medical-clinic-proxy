package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
import com.pmarko09.medical_clinic_proxy.model.dto.PatientDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final MedicalClinicProxyClient medicalClinicProxyClient;

    public List<VisitDto> getPatientVisits(String email) {
        PatientDto response = medicalClinicProxyClient.getPatient(email);

        if (response == null) {
            throw new RuntimeException("Patient with email " + email + " not found.");
        }

        return response.getVisits();
    }

    public VisitDto registerForVisit(Long visitId, Long patientId) {
        return medicalClinicProxyClient.registerForVisit(visitId, patientId);
    }
}
