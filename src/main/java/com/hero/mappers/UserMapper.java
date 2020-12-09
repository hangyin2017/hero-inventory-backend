package com.hero.mappers;

import com.hero.dtos.user.UserGetDto;
import com.hero.dtos.user.UserPostDto;
import com.hero.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserPostDto userGetDto);

    UserGetDto fromEntity(User user);

    void copy(UserGetDto userGetDto, @MappingTarget User user);
}
