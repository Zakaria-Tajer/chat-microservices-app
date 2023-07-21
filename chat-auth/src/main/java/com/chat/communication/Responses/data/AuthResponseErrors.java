package com.chat.communication.Responses.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class AuthResponseErrors {


    private String email;
    private Date timestamp;
    private int statusCode;
    private String message;

}
