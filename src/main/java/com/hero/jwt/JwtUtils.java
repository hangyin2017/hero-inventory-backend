package com.hero.jwt;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor
public class JwtUtils {

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
