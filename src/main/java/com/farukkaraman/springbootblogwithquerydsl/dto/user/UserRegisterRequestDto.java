package com.farukkaraman.springbootblogwithquerydsl.dto.user;

import lombok.Data;

@Data
public class UserRegisterRequestDto {
    private String name;
    private String email;
    private String password;
}