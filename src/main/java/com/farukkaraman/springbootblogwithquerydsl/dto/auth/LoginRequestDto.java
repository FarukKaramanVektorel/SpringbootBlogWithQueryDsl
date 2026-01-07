package com.farukkaraman.springbootblogwithquerydsl.dto.auth;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
