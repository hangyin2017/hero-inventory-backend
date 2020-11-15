package com.hero.auth;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ApplicationUserDao {
    Optional<UserDetails> getApplicationUserByName(String username);
}
