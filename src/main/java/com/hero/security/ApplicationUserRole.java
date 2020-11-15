package com.hero.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.hero.security.ApplicationUserAuthority.*;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet()),
    SALES(Sets.newHashSet(ITEMS_READ));

    private final Set<ApplicationUserAuthority> applicationUserAuthorities;

    ApplicationUserRole(Set<ApplicationUserAuthority> applicationUserAuthorities) {
        this.applicationUserAuthorities = applicationUserAuthorities;
    }

    public Set<ApplicationUserAuthority> getApplicationUserAuthorities() {
        return applicationUserAuthorities;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> authorities = getApplicationUserAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
