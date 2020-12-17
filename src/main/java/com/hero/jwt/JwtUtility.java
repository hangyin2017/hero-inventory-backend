package com.hero.jwt;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class JwtUtility {

    private final JwtConfig jwtConfig;

    public String createTokenWithExpirationTime(String subject, int tokenExpirationAfterMinutes) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusMinutes(tokenExpirationAfterMinutes)))
                .signWith(jwtConfig.secretKey())
                .compact();
    }
}
