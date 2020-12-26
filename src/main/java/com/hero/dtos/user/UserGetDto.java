package com.hero.dtos.user;

import lombok.Data;

@Data
public class UserGetDto {

    private Long id;
    private String username;
    private String email;
    private String status;
    private String role;
}
