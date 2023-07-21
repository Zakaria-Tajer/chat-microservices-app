package com.chat.communication.Responses.data;

import com.chat.communication.dto.UserLoginDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseLoginData {


    private Date timestamp;
    private int statusCode;
    private String message;
    private String token;
    private UserLoginDto userData;




}
