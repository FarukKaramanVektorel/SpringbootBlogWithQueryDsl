package com.farukkaraman.springbootblogwithquerydsl.dto.user;

import lombok.Data;

@Data
public class UserUpdateRequestDto {
    private String name;
    private String email;
    private String password;
    private String imageUrl;
}
