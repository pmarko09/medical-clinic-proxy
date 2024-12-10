package com.pmarko09.medical_clinic_proxy.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
    public DataNotFoundException() {
        super("DATA NOT FOUND!!!!!");
    }
}