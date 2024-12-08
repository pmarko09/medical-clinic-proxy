package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
import com.pmarko09.medical_clinic_proxy.model.dto.PatientIdDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    MedicalClinicProxyClient medicalClinicProxyClient;
    PatientService patientService;

    @BeforeEach
    void setup() {
        this.medicalClinicProxyClient = Mockito.mock(MedicalClinicProxyClient.class);
        this.patientService = new PatientService(medicalClinicProxyClient);
    }

    @Test
    void registerForVisit_DataCorrect_VisitDtoReturned() {
        VisitDto visitDto = new VisitDto();
        visitDto.setId(3L);
        visitDto.setAppointmentStartTime(LocalDateTime.of(2025, 04, 10, 12, 00, 00));
        visitDto.setAppointmentFinishTime(LocalDateTime.of(2025, 04, 10, 13, 00, 00));
        visitDto.setPatientId(2L);
        visitDto.setDoctorId(4L);
        PatientIdDto patientIdDto = new PatientIdDto(2L);

        when(medicalClinicProxyClient.registerForVisit(3L, patientIdDto)).thenReturn(visitDto);

        VisitDto result = patientService.registerForVisit(3L, patientIdDto);

        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals(LocalDateTime.of(2025, 4, 10, 12, 0, 0), result.getAppointmentStartTime());
        assertEquals(LocalDateTime.of(2025, 4, 10, 13, 0, 0), result.getAppointmentFinishTime());
        assertEquals(2L, result.getPatientId());
        assertEquals(4L, result.getDoctorId());
        verify(medicalClinicProxyClient).registerForVisit(3L, patientIdDto);
    }

    @Test
    void registerForVisit_VisitNotAvailable_ExceptionThrown() {
        PatientIdDto patientIdDto = new PatientIdDto(2L);

        FeignException exceptionMock = mock(FeignException.NotFound.class);
        when(exceptionMock.getMessage()).thenReturn("Visit not found");

        when(medicalClinicProxyClient.registerForVisit(3L, patientIdDto))
                .thenThrow(exceptionMock);

        FeignException exception = assertThrows(
                FeignException.class,
                () -> patientService.registerForVisit(3L, patientIdDto)
        );

        assertEquals("Visit not found", exception.getMessage());
        verify(medicalClinicProxyClient).registerForVisit(3L, patientIdDto);
    }
}