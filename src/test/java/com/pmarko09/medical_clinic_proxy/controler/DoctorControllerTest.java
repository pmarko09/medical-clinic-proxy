package com.pmarko09.medical_clinic_proxy.controler;

import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
public class DoctorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DoctorService doctorService;

    @Test
    void getAvailableVisitsForDoctor_DataCorrect_ReturnedStatus200() throws Exception {
        VisitDto visitDto1 = new VisitDto(1L,
                LocalDateTime.of(2024, 9, 22, 16, 30, 00),
                LocalDateTime.of(2024, 9, 22, 17, 00, 00),
                5L, 7L);
        VisitDto visitDto2 = new VisitDto(2L,
                LocalDateTime.of(2024, 10, 22, 16, 30, 00),
                LocalDateTime.of(2024, 10, 22, 17, 00, 00),
                5L, 11L);
        List<VisitDto> visitDtos = new ArrayList<>(List.of(visitDto1, visitDto2));

        when(doctorService.getAvailableVisitsForDoctor(5L)).thenReturn(visitDtos);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/doctors/5/available-visits"))
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].appointmentStartTime").value("2024-09-22T16:30:00"))
                .andExpect(jsonPath("$[0].appointmentFinishTime").value("2024-09-22T17:00:00"))
                .andExpect(jsonPath("$[0].doctorId").value(5L))
                .andExpect(jsonPath("$[0].patientId").value(7L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].appointmentStartTime").value("2024-10-22T16:30:00"))
                .andExpect(jsonPath("$[1].appointmentFinishTime").value("2024-10-22T17:00:00"))
                .andExpect(jsonPath("$[1].doctorId").value(5L))
                .andExpect(jsonPath("$[1].patientId").value(11L));
    }

    @Test
    void getAvailableVisitsForDoctor_NoData_ReturnedStatus200() throws Exception {
        when(doctorService.getAvailableVisitsForDoctor(5L)).thenReturn(Collections.emptyList());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/doctors/5/available-visits"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(doctorService).getAvailableVisitsForDoctor(5L);
    }
}