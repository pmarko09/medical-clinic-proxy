package com.pmarko09.medical_clinic_proxy.mapper;

import com.pmarko09.medical_clinic_proxy.model.dto.AvailableVisitDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VisitMapper {

    @Mapping(target = "doctorSpecialization", source = "specialization")
    AvailableVisitDto toAvailableVisitDto(VisitDto visitDto, String specialization);
}