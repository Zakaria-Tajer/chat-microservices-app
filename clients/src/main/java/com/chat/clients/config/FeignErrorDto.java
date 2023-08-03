package com.chat.clients.config;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeignErrorDto {
    private String timestamp;
    private int statusCode;
    private String errorMessage;
    private String path;


}
