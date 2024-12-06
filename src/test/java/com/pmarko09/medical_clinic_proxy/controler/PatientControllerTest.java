package com.pmarko09.medical_clinic_proxy.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.medical_clinic_proxy.model.dto.PatientIdDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import com.pmarko09.medical_clinic_proxy.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientService patientService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void registerForVisit_DataCorrect_ReturnedStatus200() throws Exception {
        VisitDto visitDto1 = new VisitDto(1L,
                LocalDateTime.of(2024, 9, 22, 16, 30, 00),
                LocalDateTime.of(2024, 9, 22, 17, 00, 00),
                5L, 10L);
        PatientIdDto patientIdDto = new PatientIdDto(10L);

        when(patientService.registerForVisit(1L, patientIdDto)).thenReturn(visitDto1);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/patients/register/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(patientIdDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.appointmentStartTime").value("2024-09-22T16:30:00"))
                .andExpect(jsonPath("$.appointmentFinishTime").value("2024-09-22T17:00:00"))
                .andExpect(jsonPath("$.doctorId").value(5L))
                .andExpect(jsonPath("$.patientId").value(10L));
    }
}
