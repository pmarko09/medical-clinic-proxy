package com.pmarko09.medical_clinic_proxy.config;

import feign.Client;
import feign.RetryableException;
import feign.Retryer;
import feign.httpclient.ApacheHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Client feignClient() {
        return new ApacheHttpClient();
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(1000, 2000, 3) {
            @Override
            public void continueOrPropagate(RetryableException e) {
                super.continueOrPropagate(e);
                System.out.println("Retrying after exception: " + e.getMessage());
            }
        };
    }
}
