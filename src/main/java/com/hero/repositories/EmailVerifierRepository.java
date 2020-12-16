package com.hero.repositories;

import com.hero.entities.EmailVerifier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerifierRepository extends JpaRepository<EmailVerifier, Long> {
    EmailVerifier findByUserId(Long userId);
}
