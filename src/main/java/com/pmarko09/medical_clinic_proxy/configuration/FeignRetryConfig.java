package com.pmarko09.medical_clinic_proxy.configuration;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignRetryConfig {

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(1000, 2000, 3);
    }
}