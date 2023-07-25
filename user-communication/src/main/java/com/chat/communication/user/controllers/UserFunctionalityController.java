package com.chat.communication.user.controllers;


import com.chat.communication.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class UserFunctionalityController {

    private final UserService userService;

    @PostMapping("/check")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> check(@RequestBody String userDto) {
        return ResponseEntity.ok().body(userService.checker(userDto));
    }

    @PostMapping("/check1")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> check1(@RequestBody String userDto) {
        return ResponseEntity.ok().body(userDto);
    }
}
