package com.chat.communication.dto;


import com.chat.communication.enums.Roles;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {


    private String firstName;
    private String lastName;
    private String email;

    @JsonIgnore
    private String password;
    private String image;

    @Enumerated(EnumType.STRING)
    @JsonProperty("Role")
    private Roles role;
}
