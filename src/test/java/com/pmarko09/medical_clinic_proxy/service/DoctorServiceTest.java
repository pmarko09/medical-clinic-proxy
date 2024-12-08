package com.pmarko09.medical_clinic_proxy.service;

import com.pmarko09.medical_clinic_proxy.client.MedicalClinicProxyClient;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DoctorServiceTest {

    MedicalClinicProxyClient medicalClinicProxyClient;
    DoctorService doctorService;

    @BeforeEach
    void setup() {
        this.medicalClinicProxyClient = Mockito.mock(MedicalClinicProxyClient.class);
        this.doctorService = new DoctorService(medicalClinicProxyClient);
    }

    @Test
    void getAvailableVisitsForDoctor_DataCorrect_ListVisitDtoReturned() {
        VisitDto visitDto1 = new VisitDto(1L,
                LocalDateTime.of(2024, 9, 22, 16, 30, 00),
                LocalDateTime.of(2024, 9, 22, 17, 00, 00),
                5L, 7L);
        VisitDto visitDto2 = new VisitDto(2L,
                LocalDateTime.of(2024, 10, 22, 16, 30, 00),
                LocalDateTime.of(2024, 10, 22, 17, 00, 00),
                5L, 11L);
        List<VisitDto> visitDtos = List.of(visitDto1, visitDto2);

        when(medicalClinicProxyClient.getAvailableVisitsForDoctor(5L)).thenReturn(visitDtos);

        List<VisitDto> result = doctorService.getAvailableVisitsForDoctor(5L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(visitDto1.getId(), result.get(0).getId());
        assertEquals(visitDto1.getAppointmentStartTime(), result.get(0).getAppointmentStartTime());
        assertEquals(visitDto1.getAppointmentFinishTime(), result.get(0).getAppointmentFinishTime());
        assertEquals(visitDto1.getDoctorId(), result.get(0).getDoctorId());
        assertEquals(visitDto1.getPatientId(), result.get(0).getPatientId());
        assertEquals(visitDto2.getId(), result.get(1).getId());
        assertEquals(visitDto2.getAppointmentStartTime(), result.get(1).getAppointmentStartTime());
        assertEquals(visitDto2.getAppointmentFinishTime(), result.get(1).getAppointmentFinishTime());
        assertEquals(visitDto2.getDoctorId(), result.get(1).getDoctorId());
        assertEquals(visitDto2.getPatientId(), result.get(1).getPatientId());
        verify(medicalClinicProxyClient).getAvailableVisitsForDoctor(5L);
    }

    @Test
    void getAvailableVisitsForDoctor_NoDoctorFound_ExceptionThrown() {
        FeignException mockException = mock(FeignException.NotFound.class);
        when(mockException.getMessage()).thenReturn("Doctor not found");

        when(medicalClinicProxyClient.getAvailableVisitsForDoctor(5L))
                .thenThrow(mockException);

        FeignException aThrows = assertThrows(FeignException.class,
                () -> doctorService.getAvailableVisitsForDoctor(5L));

        assertEquals("Doctor not found", aThrows.getMessage());
        verify(medicalClinicProxyClient).getAvailableVisitsForDoctor(5L);
    }

    @Test
    void getAvailableVisitsForDoctor_NoVisits_EmptyVisitDtoListReturned() {
        when(medicalClinicProxyClient.getAvailableVisitsForDoctor(4L))
                .thenReturn(Collections.emptyList());

        List<VisitDto> availableVisitsForDoctor = doctorService.getAvailableVisitsForDoctor(4L);

        assertTrue(availableVisitsForDoctor.isEmpty());
        verify(medicalClinicProxyClient).getAvailableVisitsForDoctor(4L);
    }
}