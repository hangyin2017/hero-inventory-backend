package com.hero.mappers;

import com.hero.dtos.user.UserGetDto;
import com.hero.dtos.user.UserPostDto;
import com.hero.entities.Authority;
import com.hero.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserPostDto userGetDto);

    @Mapping(source = "authorities", target = "role")
    UserGetDto fromEntity(User user);

    void copy(UserGetDto userGetDto, @MappingTarget User user);

    default String authorityToRole(Set<Authority> authorities) {
        if(authorities == null || authorities.size() == 0) { return null; }
        List<String> roles = authorities.stream()
                .map(authority -> authority.getPermission())
                .filter(permission -> permission.startsWith("ROLE_"))
                .collect(Collectors.toList());
        return roles.get(0).replace("ROLE_", "").toLowerCase();
    }
}
