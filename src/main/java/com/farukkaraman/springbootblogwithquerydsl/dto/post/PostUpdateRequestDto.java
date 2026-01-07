package com.farukkaraman.springbootblogwithquerydsl.dto.post;

import lombok.Data;

@Data
public class PostUpdateRequestDto {
    private String title;
    private String content;
    private Boolean published;
}
