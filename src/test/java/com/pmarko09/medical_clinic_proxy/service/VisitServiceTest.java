package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
import com.pmarko09.medical_clinic_proxy.model.dto.AvailableVisitDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VisitServiceTest {

    MedicalClinicProxyClient medicalClinicProxyClient;
    VisitService visitService;

    @BeforeEach
    void setup() {
        this.medicalClinicProxyClient = Mockito.mock(MedicalClinicProxyClient.class);
        this.visitService = new VisitService(medicalClinicProxyClient);
    }

    @Test
    void getAvailableAppointments_DataCorrect_ListAvailableVisitDtoReturned() {
        VisitDto visitDto1 = new VisitDto();
        visitDto1.setAppointmentStartTime(LocalDateTime.of(2025, 4, 10, 12, 0, 0));
        visitDto1.setAppointmentFinishTime(LocalDateTime.of(2025, 4, 10, 13, 0, 0));
        VisitDto visitDto2 = new VisitDto();
        visitDto2.setAppointmentStartTime(LocalDateTime.of(2025, 4, 20, 12, 0, 0));
        visitDto2.setAppointmentFinishTime(LocalDateTime.of(2025, 4, 20, 13, 0, 0));
        List<VisitDto> visitDtos = List.of(visitDto1, visitDto2);
        LocalDate appDate = LocalDate.of(2025, 4, 10);

        when(medicalClinicProxyClient.getAvailableAppointments("dentist", appDate))
                .thenReturn(visitDtos);

        List<AvailableVisitDto> result = visitService.getAvailableAppointments("dentist", appDate);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("dentist", result.get(0).getDoctorSpecialization());
        assertEquals(LocalDateTime.of(2025, 4, 10, 12, 0, 0), result.get(0).getAppointmentStartTime());
        assertEquals(LocalDateTime.of(2025, 4, 10, 13, 0, 0), result.get(0).getAppointmentFinishTime());
    }

    @Test
    void getAvailableAppointments_NoData_ReturnedEmptyList() {
        LocalDate appDate = LocalDate.of(2025, 4, 10);

        when(medicalClinicProxyClient.getAvailableAppointments("dentist", appDate))
                .thenReturn(Collections.emptyList());
        List<AvailableVisitDto> result = visitService.getAvailableAppointments("dentist", appDate);

        assertTrue(result.isEmpty());
        verify(medicalClinicProxyClient).getAvailableAppointments("dentist", appDate);
    }

    @Test
    void getPatientVisits_DataCorrect_ListVisitDtoReturned() {
        VisitDto visitDto1 = new VisitDto(1L,
                LocalDateTime.of(2024, 9, 22, 16, 30, 00),
                LocalDateTime.of(2024, 9, 22, 17, 00, 00),
                5L, 11L);
        VisitDto visitDto2 = new VisitDto(2L,
                LocalDateTime.of(2024, 10, 22, 16, 30, 00),
                LocalDateTime.of(2024, 10, 22, 17, 00, 00),
                5L, 11L);
        List<VisitDto> visitDtos = List.of(visitDto1, visitDto2);

        when(medicalClinicProxyClient.getVisits(11L)).thenReturn(visitDtos);

        List<VisitDto> result = visitService.getPatientVisits(11L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(LocalDateTime.of(2024, 9, 22, 16, 30, 00), result.get(0).getAppointmentStartTime());
        assertEquals(LocalDateTime.of(2024, 9, 22, 17, 00, 00), result.get(0).getAppointmentFinishTime());
        assertEquals(5L, result.get(0).getDoctorId());
        assertEquals(11L, result.get(0).getPatientId());
        assertEquals(2L, result.get(1).getId());
        assertEquals(LocalDateTime.of(2024, 10, 22, 16, 30, 00), result.get(1).getAppointmentStartTime());
        assertEquals(LocalDateTime.of(2024, 10, 22, 17, 00, 00), result.get(1).getAppointmentFinishTime());
        assertEquals(5L, result.get(1).getDoctorId());
        assertEquals(11L, result.get(1).getPatientId());
        verify(medicalClinicProxyClient).getVisits(11L);
    }

    @Test
    void getPatientVisits_NoData_ReturnedEmptyList() {
        when(medicalClinicProxyClient.getVisits(5L))
                .thenReturn(Collections.emptyList());

        List<VisitDto> result = visitService.getPatientVisits(5L);

        assertTrue(result.isEmpty());
        verify(medicalClinicProxyClient).getVisits(5L);
    }
}

