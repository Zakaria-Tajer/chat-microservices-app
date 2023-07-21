package com.chat.communication.Responses.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDefault {

    private Date timestamp;
    private int statusCode;
    private String message;
}
