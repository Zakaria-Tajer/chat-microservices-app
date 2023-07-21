package com.chat.communication.Responses.data;

import com.chat.communication.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseData {

    private Date timestamp;
    private int statusCode;
    private String message;
    private UserDto userData;

}
