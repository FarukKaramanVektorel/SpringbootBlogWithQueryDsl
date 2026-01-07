package com.farukkaraman.springbootblogwithquerydsl.mapper;

import com.farukkaraman.springbootblogwithquerydsl.dto.post.PostCreateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.post.PostResponseDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.post.PostUpdateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.entity.Post;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "authorName", source = "user.name")
    @Mapping(target = "createdDate", source = "createdAt")
    PostResponseDto toDto(Post post);

    List<PostResponseDto> toDtoList(List<Post> posts);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "published", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Post toEntity(PostCreateRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(PostUpdateRequestDto dto, @MappingTarget Post post);
}
