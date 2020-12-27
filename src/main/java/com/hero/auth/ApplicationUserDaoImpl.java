package com.hero.auth;

import com.hero.entities.Authority;
import com.hero.entities.User;
import com.hero.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository()
public class ApplicationUserDaoImpl implements ApplicationUserDao {

    @Autowired
    private UserService userService;

    @Override
    public Optional<UserDetails> getApplicationUserByName(String username) {
        User user = userService.findUserByUsername(username);

        UserDetails userDetails = new ApplicationUser(
                user.getUsername(),
                user.getEncodedPassword(),
                generateGrantedAuthority(user.getAuthorities()),
                true,
                true,
                true,
                true
        );

        return Optional.of(userDetails);
    }

    private Set<SimpleGrantedAuthority> generateGrantedAuthority(Set<Authority> authorities) {
        Set<SimpleGrantedAuthority> permissions = authorities.stream()
                .map(authotiry -> new SimpleGrantedAuthority(authotiry.getPermission()))
                .collect(Collectors.toSet());

        return permissions;
    }
}
