package com.chat.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authentication", url = "http://localhost:9081")
public interface AuthenticationServiceClient {

    @GetMapping("/api/v1/auth/verify-token")
    TokenIsValidDto verifyToken(@RequestHeader("Authorization") String authorizationHeader);

}
