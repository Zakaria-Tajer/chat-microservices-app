package com.chat.communication.user.services;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    public String checker(String userDto) {
        return userDto;
    }
}
