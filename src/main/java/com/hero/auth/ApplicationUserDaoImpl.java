package com.hero.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.hero.security.ApplicationUserRole.ADMIN;
import static com.hero.security.ApplicationUserRole.SALES;

@Repository()
public class ApplicationUserDaoImpl implements ApplicationUserDao {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserDetails> getApplicationUserByName(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<UserDetails> getApplicationUsers() {
        List<UserDetails> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        "jason",
                        passwordEncoder.encode("password"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "tom",
                        passwordEncoder.encode("password"),
                        SALES.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );

        return applicationUsers;
    }
}
