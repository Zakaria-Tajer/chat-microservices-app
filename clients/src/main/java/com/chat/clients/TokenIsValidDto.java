package com.chat.clients;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenIsValidDto {


    private boolean isValid;
    private List<String> role;
}
