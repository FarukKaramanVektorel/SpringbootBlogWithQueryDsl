package com.farukkaraman.springbootblogwithquerydsl.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto {
    private String accessToken;
    private String tokenType;
    private Long userId;
    private String email;
    private String name;

    public static AuthResponseDto of(String accessToken, Long userId, String email, String name) {
        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .userId(userId)
                .email(email)
                .name(name)
                .build();
    }
}
