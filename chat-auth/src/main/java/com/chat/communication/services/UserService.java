package com.chat.communication.services;

import com.chat.communication.Responses.data.AuthResponseData;
import com.chat.communication.dto.TokenIsValidDto;
import com.chat.communication.dto.UserDto;

public interface UserService {

    AuthResponseData registerUser(UserDto userDto);

    Object loginUser(UserDto loginDto);

    TokenIsValidDto verifyToken(String authorizationHeader);


}
