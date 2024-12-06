package com.pmarko09.medical_clinic_proxy.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWireMock(port = 8089)
public class DoctorApiTest {

    @Autowired
    WireMockServer wireMockServer;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        wireMockServer.start();
        restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:8079")
                .build();
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.resetAll();
        wireMockServer.stop();
    }

    @Test
    void getAvailableVisitsForDoctor_DataCorrect_VisitDtosReturned() throws JsonProcessingException {
        VisitDto visitDto1 = new VisitDto(1L,
                LocalDateTime.of(2024, 9, 22, 16, 30, 00),
                LocalDateTime.of(2024, 9, 22, 17, 00, 00),
                3L, 7L);
        VisitDto visitDto2 = new VisitDto(2L,
                LocalDateTime.of(2024, 10, 22, 16, 30, 00),
                LocalDateTime.of(2024, 10, 22, 17, 00, 00),
                3L, 7L);
        List<VisitDto> visitDtos = new ArrayList<>(List.of(visitDto1, visitDto2));

        wireMockServer.stubFor(WireMock.get("/appointments/doctor/3/available")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(visitDtos))));

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8079")
                .path("/doctors/3/available-visits")
                .toUriString();

        ResponseEntity<VisitDto[]> response = restTemplate.getForEntity(url, VisitDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        VisitDto[] result = response.getBody();
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals(result[0].getId(), visitDto1.getId());
        assertEquals(result[1].getId(), visitDto2.getId());
        assertTrue(result[0].getDoctorId().equals(result[1].getDoctorId()));
    }

    @Test
    void getAvailableVisitsForDoctor_NoData_ReturnedStatus200() throws JsonProcessingException {
        wireMockServer.stubFor(WireMock.get("/appointments/doctor/3/available")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("[]")));

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8079")
                .path("/doctors/3/available-visits")
                .toUriString();

        ResponseEntity<VisitDto[]> response = restTemplate.getForEntity(url, VisitDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        VisitDto[] result = response.getBody();
        assertNotNull(result);
        assertEquals(0, result.length);
    }

}
