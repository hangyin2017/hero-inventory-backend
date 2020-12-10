package com.hero.dtos.user;

import lombok.Data;

@Data
public class UserPostDto {

    private String username;
    private String password;
    private String email;
    private String status;
}
