package com.farukkaraman.springbootblogwithquerydsl.mapper;

import com.farukkaraman.springbootblogwithquerydsl.dto.user.UserRegisterRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.user.UserResponseDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.user.UserUpdateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", expression = "java(user.getRole().name())")
    @Mapping(target = "provider", expression = "java(user.getProvider().name())")
    UserResponseDto toDto(User user);

    List<UserResponseDto> toDtoList(List<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "providerId", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    User toEntity(UserRegisterRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "providerId", ignore = true)
    void updateEntityFromDto(UserUpdateRequestDto dto, @MappingTarget User user);
}
