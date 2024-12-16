package com.pmarko09.medical_clinic_proxy.mapper;

import com.pmarko09.medical_clinic_proxy.model.dto.AvailableVisitDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class VisitMapperTest {

    VisitMapper visitMapper = Mappers.getMapper(VisitMapper.class);

    @Test
    void mapToAvailableVisitDto() {
        log.info("Starting test: mapToAvailableVisitDto");
        VisitDto visitDto = new VisitDto(
                1L,
                LocalDateTime.of(2024, 9, 22, 16, 30),
                LocalDateTime.of(2024, 9, 22, 17, 00),
                3L,
                7L
        );
        String specialization = "Dentist";

        AvailableVisitDto result = visitMapper.toAvailableVisitDto(visitDto, specialization);

        assertNotNull(result);
        assertEquals(visitDto.getAppointmentStartTime(), result.getAppointmentStartTime());
        assertEquals(visitDto.getAppointmentFinishTime(), result.getAppointmentFinishTime());
        assertEquals(specialization, result.getDoctorSpecialization());
        log.info("Test completed: mapToAvailableVisitDto");
    }
}