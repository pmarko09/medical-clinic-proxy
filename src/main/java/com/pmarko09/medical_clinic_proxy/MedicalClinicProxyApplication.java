package com.pmarko09.medical_clinic_proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MedicalClinicProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalClinicProxyApplication.class, args);
	}

}