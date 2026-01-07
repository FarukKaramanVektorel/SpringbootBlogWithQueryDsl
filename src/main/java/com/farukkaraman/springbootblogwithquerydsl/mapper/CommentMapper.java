package com.farukkaraman.springbootblogwithquerydsl.mapper;

import com.farukkaraman.springbootblogwithquerydsl.dto.comment.CommentCreateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.comment.CommentResponseDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.comment.CommentUpdateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.entity.Comment;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "authorName", source = "user.name")
    @Mapping(target = "createdDate", source = "createdAt")
    CommentResponseDto toDto(Comment comment);

    List<CommentResponseDto> toDtoList(List<Comment> comments);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Comment toEntity(CommentCreateRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(CommentUpdateRequestDto dto, @MappingTarget Comment comment);
}
