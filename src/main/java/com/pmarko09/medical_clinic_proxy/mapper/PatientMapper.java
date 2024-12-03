package com.pmarko09.medical_clinic_proxy.mapper;

import com.pmarko09.medical_clinic_proxy.model.dto.PatientDto;
import com.pmarko09.medical_clinic_proxy.model.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDto toDto(Patient patient);
}
