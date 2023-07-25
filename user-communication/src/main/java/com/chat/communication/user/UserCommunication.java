package com.chat.communication.user;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableDiscoveryClient
@RequiredArgsConstructor
@EnableFeignClients(basePackages = "com.chat.clients")

public class UserCommunication {


    public static void main(String[] args) {
        SpringApplication.run(UserCommunication.class, args);
    }




}
