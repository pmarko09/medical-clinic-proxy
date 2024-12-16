package com.pmarko09.medical_clinic_proxy.controler;

import com.pmarko09.medical_clinic_proxy.model.dto.AvailableVisitDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.service.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class VisitControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    VisitService visitService;

    @Test
    void getAvailableAppointments_DataCorrect_ReturnedStatus200() throws Exception {
        log.info("Starting test: getAvailableAppointments_DataCorrect_ReturnedStatus200");
        AvailableVisitDto availableVisitDto1 = new AvailableVisitDto(
                LocalDateTime.of(2024, 9, 22, 16, 30, 00),
                LocalDateTime.of(2024, 9, 22, 17, 00, 00),
                "DD");
        AvailableVisitDto availableVisitDto2 = new AvailableVisitDto(
                LocalDateTime.of(2024, 9, 25, 20, 30, 00),
                LocalDateTime.of(2024, 9, 25, 21, 00, 00),
                "CC");
        List<AvailableVisitDto> visitDtos = new ArrayList<>(List.of(availableVisitDto1, availableVisitDto2));

        when(visitService.getAvailableAppointments("DD", LocalDate.of(2024, 9, 22)))
                .thenReturn(visitDtos);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/appointments/available")
                                .param("specialization", "DD")
                                .param("date", "2024-09-22")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].doctorSpecialization").value("DD"))
                .andExpect(jsonPath("$[1].doctorSpecialization").value("CC"));
        log.info("Test completed: getAvailableAppointments_DataCorrect_ReturnedStatus200");
    }

    @Test
    void getAvailableAppointments_NoData_ReturnedStatus200() throws Exception {
        log.info("Starting test: getAvailableAppointments_NoData_ReturnedStatus200");
        when(visitService.getAvailableAppointments("DD", LocalDate.of(2024, 9, 22)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/appointments/available")
                                .param("specialization", "DD")
                                .param("date", "2024-09-22")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(visitService).getAvailableAppointments("DD", LocalDate.of(2024, 9, 22));
        log.info("Test completed: getAvailableAppointments_NoData_ReturnedStatus200");
    }

    @Test
    void getPatientVisits_DataCorrect_ReturnedStatus200() throws Exception {
        log.info("Starting test: getPatientVisits_DataCorrect_ReturnedStatus200");
        VisitDto visitDto1 = new VisitDto(1L,
                LocalDateTime.of(2024, 9, 22, 16, 30, 00),
                LocalDateTime.of(2024, 9, 22, 17, 00, 00),
                5L, 7L);
        VisitDto visitDto2 = new VisitDto(2L,
                LocalDateTime.of(2024, 10, 22, 16, 30, 00),
                LocalDateTime.of(2024, 10, 22, 17, 00, 00),
                6L, 7L);
        List<VisitDto> visitDtos = new ArrayList<>(List.of(visitDto1, visitDto2));

        when(visitService.getPatientVisits(7L)).thenReturn(visitDtos);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/appointments")
                                .param("patientId", "7")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].appointmentStartTime").value("2024-09-22T16:30:00"))
                .andExpect(jsonPath("$[0].appointmentFinishTime").value("2024-09-22T17:00:00"))
                .andExpect(jsonPath("$[0].doctorId").value(5L))
                .andExpect(jsonPath("$[0].patientId").value(7L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].appointmentStartTime").value("2024-10-22T16:30:00"))
                .andExpect(jsonPath("$[1].appointmentFinishTime").value("2024-10-22T17:00:00"))
                .andExpect(jsonPath("$[1].doctorId").value(6L))
                .andExpect(jsonPath("$[1].patientId").value(7L));
        log.info("Test completed: getPatientVisits_DataCorrect_ReturnedStatus200");
    }

    @Test
    void getPatientVisits_NoData_ReturnedStatus200() throws Exception {
        log.info("Starting test: getPatientVisits_NoData_ReturnedStatus200");
        when(visitService.getPatientVisits(7L))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/appointments")
                                .param("patientId", "7")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(visitService).getPatientVisits(7L);
        log.info("Test completed: getPatientVisits_NoData_ReturnedStatus200");
    }
}