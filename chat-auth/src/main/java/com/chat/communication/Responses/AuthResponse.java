package com.chat.communication.Responses;

import com.chat.communication.Responses.data.AuthResponseData;
import com.chat.communication.Responses.data.AuthResponseDefault;
import com.chat.communication.Responses.data.AuthResponseErrors;
import com.chat.communication.Responses.data.AuthResponseLoginData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AuthResponse {

    public AuthResponseData registerResponse(AuthResponseData registerResponse) {
        return new AuthResponseData(
                registerResponse.getTimestamp(),
                registerResponse.getStatusCode(),
                registerResponse.getMessage(),
                registerResponse.getUserData()
        );
    }

    public AuthResponseLoginData loginResponse(AuthResponseLoginData loginResponse) {
        return new AuthResponseLoginData(
                loginResponse.getTimestamp(),
                loginResponse.getStatusCode(),
                loginResponse.getMessage(),
                loginResponse.getToken(),
                loginResponse.getUserData()
        );
    }

    public AuthResponseErrors authResponseErrors(AuthResponseErrors responseErrors) {
        return new AuthResponseErrors(
                responseErrors.getEmail(),
                responseErrors.getTimestamp(),
                responseErrors.getStatusCode(),
                responseErrors.getMessage()
        );
    }

    public AuthResponseDefault authResponseDefaultError(AuthResponseDefault responseErrors) {
        return new AuthResponseDefault(
                responseErrors.getTimestamp(),
                responseErrors.getStatusCode(),
                responseErrors.getMessage()
        );
    }
}
