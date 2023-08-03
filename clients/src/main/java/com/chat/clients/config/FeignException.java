package com.chat.clients.config;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeignException extends RuntimeException{

    private final FeignErrorDto feignErrorDto;

}

