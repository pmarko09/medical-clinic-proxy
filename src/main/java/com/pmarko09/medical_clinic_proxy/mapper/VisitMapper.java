package com.pmarko09.medical_clinic_proxy.mapper;

import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.model.entity.Visit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitMapper {

    VisitDto toDto(Visit visit);
}
