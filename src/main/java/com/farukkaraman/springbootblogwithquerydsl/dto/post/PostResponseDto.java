package com.farukkaraman.springbootblogwithquerydsl.dto.post;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private Long viewCount;
    private LocalDateTime createdDate;
}