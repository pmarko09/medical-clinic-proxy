package com.pmarko09.medical_clinic_proxy.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.pmarko09.medical_clinic_proxy.model.dto.PatientIdDto;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWireMock(port = 8089)
public class PatientApiTest {

    @Autowired
    WireMockServer wireMockServer;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
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
    void registerForVisit_DataCorrect_VisitDtoReturned() throws JsonProcessingException {
        VisitDto visitDto1 = new VisitDto(1L,
                LocalDateTime.of(2024, 9, 22, 16, 30, 00),
                LocalDateTime.of(2024, 9, 22, 17, 00, 00),
                3L, 7L);
        PatientIdDto patientIdDto = new PatientIdDto(7L);

        wireMockServer.stubFor(WireMock.patch(WireMock.urlEqualTo("/appointments/1"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(visitDto1))));

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8079")
                .path("/patients/register/1")
                .toUriString();

        VisitDto result = restTemplate.patchForObject(url, patientIdDto, VisitDto.class);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(3L, result.getDoctorId());
        assertEquals(7L, result.getPatientId());
    }
}