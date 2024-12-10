package com.pmarko09.medical_clinic_proxy.retry;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.pmarko09.medical_clinic_proxy.model.dto.VisitDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWireMock(port = 8089)
public class RetryTest {

    @Autowired
    WireMockServer wireMockServer;

    @Autowired
    RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        wireMockServer.start();
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.resetAll();
        wireMockServer.stop();
    }

    @Test
    void onceError500TriggerRetry() {
        wireMockServer.stubFor(WireMock.get("/appointments/doctor/33/available")
                .willReturn(WireMock.aResponse()
                        .withStatus(500)
                        .withHeader(HttpHeaders.RETRY_AFTER, "3")));

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8079")
                .path("/doctors/33/available-visits")
                .toUriString();

        Assertions.assertThrows(Exception.class, () -> restTemplate.getForEntity(url, VisitDto[].class));

        wireMockServer.verify(3, getRequestedFor(urlEqualTo("/appointments/doctor/33/available")));
    }

    @Test
    void onceError503TriggerRetry() {
        wireMockServer.stubFor(WireMock.get("/appointments/doctor/3/available")
                .willReturn(WireMock.aResponse()
                        .withStatus(503)
                        .withHeader(HttpHeaders.RETRY_AFTER, "3")));

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8079")
                .path("/doctors/3/available-visits")
                .toUriString();

        Assertions.assertThrows(Exception.class, () -> restTemplate.getForEntity(url, VisitDto[].class));

        wireMockServer.verify(3, getRequestedFor(urlEqualTo("/appointments/doctor/3/available")));
    }

    @Test
    void nonRetryableErrorDoesNotTriggerRetry() {
        WireMock.stubFor(WireMock.get("/appointments/doctor/3/available")
                .willReturn(WireMock.aResponse()
                        .withStatus(400)));

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8079")
                .path("/doctors/3/available-visits")
                .toUriString();

        Assertions.assertThrows(Exception.class, () ->
                restTemplate.getForEntity(url, VisitDto[].class)
        );

        WireMock.verify(1, getRequestedFor(urlEqualTo("/appointments/doctor/3/available")));
    }

    @Test
    void onceNoDataThrowDataNotFoundException() {
        WireMock.stubFor(WireMock.get("/appointments?patientId=7")
                .willReturn(WireMock.aResponse()
                        .withStatus(404)));

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8079/appointments")
                .queryParam("patientId", "7")
                .toUriString();

        Exception exception = Assertions.assertThrows(Exception.class,
                () -> restTemplate.getForEntity(url, VisitDto[].class));

        Assertions.assertTrue(exception.getMessage().contains("DATA NOT FOUND!!!!!"));
        WireMock.verify(1, getRequestedFor(urlEqualTo("/appointments?patientId=7")));
    }
}