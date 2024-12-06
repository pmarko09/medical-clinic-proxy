package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final MedicalClinicProxyClient medicalClinicProxyClient;

    public List<VisitDto> getAvailableVisitsForDoctor(Long doctorId) {
        return medicalClinicProxyClient.getAvailableVisitsForDoctor(doctorId);
    }
}
