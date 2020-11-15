package com.hero.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.hero.security.ApplicationUserAuthority.ITEMS_WRITE;
import static com.hero.security.ApplicationUserRole.*;

@EnableWebSecurity
@Configuration
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/*/items/**").hasAuthority(ITEMS_WRITE.getAuthority())
                .antMatchers(HttpMethod.PUT, "/api/*/items/**").hasAuthority(ITEMS_WRITE.getAuthority())
                .antMatchers(HttpMethod.DELETE, "/api/*/items/**").hasAuthority(ITEMS_WRITE.getAuthority())
                .antMatchers("/api/*/items/filter*").hasAuthority(ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails jasonUser = User.builder()
                .username("jason")
                .password(passwordEncoder.encode("password"))
                .roles(ADMIN.name())
                .build();

        UserDetails tomUser = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("password"))
                .roles(SALES.name())
                .build();

        return new InMemoryUserDetailsManager(
                jasonUser,
                tomUser
        );
    }
}
