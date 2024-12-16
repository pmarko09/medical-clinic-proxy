package com.pmarko09.medical_clinic_proxy.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.pmarko09.medical_clinic_proxy.model.dto.AvailableVisitDto;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWireMock(port = 8089)
public class VisitApiTest {

    @Autowired
    WireMockServer wireMockServer;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @AfterEach
    public void tearDown() {
        wireMockServer.resetAll();
    }

    @Test
    void getAvailableAppointments_DataCorrect_AvailableVisitDtoListReturned() throws JsonProcessingException {
        AvailableVisitDto availableVisitDto1 = new AvailableVisitDto();
        availableVisitDto1.setDoctorSpecialization("dentist");
        availableVisitDto1.setAppointmentStartTime(LocalDateTime.of(2024, 9, 22, 16, 30, 00));
        availableVisitDto1.setAppointmentFinishTime(LocalDateTime.of(2024, 9, 22, 19, 30, 00));
        AvailableVisitDto availableVisitDto2 = new AvailableVisitDto();
        availableVisitDto2.setDoctorSpecialization("dentist");
        availableVisitDto2.setAppointmentStartTime(LocalDateTime.of(2024, 9, 22, 20, 30, 00));
        availableVisitDto2.setAppointmentFinishTime(LocalDateTime.of(2024, 9, 22, 21, 30, 00));
        List<AvailableVisitDto> availableVisits = List.of(availableVisitDto1, availableVisitDto2);
        LocalDate date = LocalDate.of(2024, 9, 22);

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(
                        "/appointments/available?specialization=dentist&date=2024-09-22"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(availableVisits))));

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8079/appointments/available")
                .queryParam("specialization", "dentist")
                .queryParam("date", date.toString())
                .toUriString();

        ResponseEntity<AvailableVisitDto[]> response = restTemplate.getForEntity(url, AvailableVisitDto[].class);
        AvailableVisitDto[] result = response.getBody();
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals("dentist", result[0].getDoctorSpecialization());
        assertEquals("dentist", result[1].getDoctorSpecialization());
    }

    @Test
    void getPatientVisits_DataCorrect_ListVisitDtoReturned() throws JsonProcessingException {
        VisitDto visitDto1 = new VisitDto(1L,
                LocalDateTime.of(2024, 9, 22, 16, 30, 00),
                LocalDateTime.of(2024, 9, 22, 17, 00, 00),
                3L, 7L);
        VisitDto visitDto2 = new VisitDto(2L,
                LocalDateTime.of(2024, 10, 22, 16, 30, 00),
                LocalDateTime.of(2024, 10, 22, 17, 00, 00),
                5L, 7L);
        List<VisitDto> visitDtos = new ArrayList<>(List.of(visitDto1, visitDto2));

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(
                        "/appointments?patientId=7"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(visitDtos))));

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8079/appointments")
                .queryParam("patientId", "7")
                .toUriString();

        ResponseEntity<VisitDto[]> response = restTemplate.getForEntity(url, VisitDto[].class);

        VisitDto[] result = response.getBody();
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals(1L, result[0].getId());
        assertEquals(3L, result[0].getDoctorId());
        assertEquals(2L, result[1].getId());
        assertEquals(5L, result[1].getDoctorId());
    }
}