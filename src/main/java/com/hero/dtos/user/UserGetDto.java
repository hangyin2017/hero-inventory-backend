package com.hero.dtos.user;

import com.hero.entities.Authority;
import lombok.Data;

import java.util.Set;

@Data
public class UserGetDto {

    private Long id;
    private String username;
    private String email;
    private String status;
    private String role;
    private Set<Authority> authorities;
}
