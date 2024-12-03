package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.mapper.VisitMapper;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.model.entity.Patient;
import com.pmarko09.medical_clinic_proxy.repository.PatientRepository;
import com.pmarko09.medical_clinic_proxy.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final VisitMapper visitMapper;


}
