package com.pmarko09.medical_clinic_proxy.exception;

public class MedicalClinicServiceUnavailableException extends RuntimeException {
    public MedicalClinicServiceUnavailableException(String message) {
        super(message);
    }
}