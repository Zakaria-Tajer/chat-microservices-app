package com.chat.clients.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

import java.io.IOException;

public class FeignConfig implements ErrorDecoder{
    @Override
    public Exception decode(String methodKey, Response response) {
        // Implement the logic to map HTTP error response to custom exception
        // For example, you can deserialize the response body to your custom error class
        if (response.status() == 500) {
            System.out.println("heeeeeeeeeeeeeeeeeeeelooo");
            FeignErrorDto errorResponse = deserializeError(response);
            return new FeignException(errorResponse);
        } else if (response.status() == 404) {
            // Handle another type of error
        }

        // If the status code is not mapped to any custom error, return the default error
        return new ErrorDecoder.Default().decode(methodKey, response);
    }

    // Implement the method to deserialize the error response
    private FeignErrorDto deserializeError(Response response) {
        try {
            String responseBody = Util.toString(response.body().asReader());
            ObjectMapper objectMapper = new ObjectMapper(); // Or use your preferred JSON library
            return objectMapper.readValue(responseBody, FeignErrorDto.class);
        } catch (IOException e) {
            // Handle deserialization error, if any
            e.printStackTrace();
            return null;
        }


    }
}
