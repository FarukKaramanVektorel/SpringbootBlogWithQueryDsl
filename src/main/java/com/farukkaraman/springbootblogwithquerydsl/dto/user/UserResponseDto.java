package com.farukkaraman.springbootblogwithquerydsl.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String imageUrl;
    private String role;
    private String provider;
}
