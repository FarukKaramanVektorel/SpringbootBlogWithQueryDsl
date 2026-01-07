package com.farukkaraman.springbootblogwithquerydsl.dto.comment;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDto {
    private Long id;
    private String content;
    private String authorName;
    private LocalDateTime createdDate;
}