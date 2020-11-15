package com.hero.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.hero.security.ApplicationUserRole.ADMIN;
import static com.hero.security.ApplicationUserRole.SALES;

@Service
public class ApplicationUsersService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ArrayList<UserDetails> users = new ArrayList<>();

        UserDetails jasonUser = User.builder()
                .username("jason")
                .password(passwordEncoder.encode("password"))
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails tomUser = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("password"))
                .authorities(SALES.getGrantedAuthorities())
                .build();

        users.add(jasonUser);
        users.add(tomUser);

        return users.stream()
                .filter(userDetails -> username.equals(userDetails.getUsername()))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }
}
