package com.pmarko09.medical_clinic_proxy.config;

import com.pmarko09.medical_clinic_proxy.exception.DataNotFoundException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = feign.FeignException.errorStatus(methodKey, response);
        int status = response.status();
        if (status == 500 || status == 503) {
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    0L,
                    response.request());
        }
        if (status == 404) {
            return new DataNotFoundException();
        }
        return exception;
    }
}