package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
import com.pmarko09.medical_clinic_proxy.mapper.VisitMapper;
import com.pmarko09.medical_clinic_proxy.model.dto.DoctorDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final VisitMapper visitMapper;
    private final MedicalClinicProxyClient medicalClinicProxyClient;

    public List<VisitDto> getAvailableVisitsForDoctor(String email) {
        DoctorDto doctor = medicalClinicProxyClient.getDoctor(email);
        if (doctor == null) {
            throw new RuntimeException("Doctor with email " + email + " not found.");
        }

        return medicalClinicProxyClient.getAvailableVisitsForDoctor(doctor.getId());
    }
}
