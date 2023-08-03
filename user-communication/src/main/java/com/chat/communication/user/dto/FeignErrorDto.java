package com.chat.communication.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeignErrorDto {
    private String timestamp;
    private int statusCode;
    private String errorMessage;
    private String path;

}
