package com.chat.communication.controllers;


import com.chat.communication.Responses.data.AuthResponseData;
import com.chat.communication.dto.TokenIsValidDto;
import com.chat.communication.dto.UserDto;
import com.chat.communication.services.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class UserController {


    private final UserServiceImp userServiceImp;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseData> adminRegistration(@RequestBody UserDto userDto){
        return ResponseEntity.ok().body(userServiceImp.registerUser(userDto));
    }

    @PostMapping("login")
    public ResponseEntity<Object> loginAdmin(@RequestBody UserDto loginDto){
        return ResponseEntity.ok().body(userServiceImp.loginUser(loginDto));
    }

    @GetMapping("/verify-token")
    @PreAuthorize("hasRole('USER')")
    public TokenIsValidDto verifyJwtToken(@RequestHeader("Authorization") String authorizationHeader) {
       return userServiceImp.verifyToken(authorizationHeader);
    }




}