package com.farukkaraman.springbootblogwithquerydsl.dto.comment;

import lombok.Data;

@Data
public class CommentCreateRequestDto {
    private String content;
    private Long postId;
    private Long userId;
}