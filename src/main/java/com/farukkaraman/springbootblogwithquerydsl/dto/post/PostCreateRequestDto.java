package com.farukkaraman.springbootblogwithquerydsl.dto.post;

import lombok.Data;

@Data
public class PostCreateRequestDto {
    private String title;
    private String content;
    private Long userId;
}
