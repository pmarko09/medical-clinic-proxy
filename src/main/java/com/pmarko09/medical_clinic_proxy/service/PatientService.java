package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
import com.pmarko09.medical_clinic_proxy.mapper.PatientMapper;
import com.pmarko09.medical_clinic_proxy.mapper.VisitMapper;
import com.pmarko09.medical_clinic_proxy.model.dto.PatientDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.model.entity.Patient;
import com.pmarko09.medical_clinic_proxy.model.entity.Visit;
import com.pmarko09.medical_clinic_proxy.repository.PatientRepository;
import com.pmarko09.medical_clinic_proxy.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;
    private final MedicalClinicProxyClient medicalClinicProxyClient;
    private PatientMapper patientMapper;
    private VisitMapper visitMapper;

    public List<VisitDto> getPatientVisits(String email) {
        PatientDto response = medicalClinicProxyClient.getPatient(email);

        if (response == null) {
            throw new RuntimeException("Patient with email " + email + " not found.");
        }

        return response.getVisits().stream()
                .map(visitMapper::toDto)
                .toList();
    }

    public VisitDto registerForVisit(Long visitId, Long patientId) {
        return medicalClinicProxyClient.registerForVisit(visitId, patientId);
    }

}
