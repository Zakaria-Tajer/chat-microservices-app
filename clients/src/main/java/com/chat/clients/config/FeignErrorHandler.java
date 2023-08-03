package com.chat.clients.config;


import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignErrorHandler {

    @Bean
    public ErrorDecoder errorDecoder() {
        return (ErrorDecoder) new FeignErrorDto();
    }

}
