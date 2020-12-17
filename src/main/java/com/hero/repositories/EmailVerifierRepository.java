package com.hero.repositories;

import com.hero.entities.EmailVerifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmailVerifierRepository extends JpaRepository<EmailVerifier, Long> {
    EmailVerifier findByUserId(Long userId);

    EmailVerifier findByToken(String token);

    @Modifying
    @Query(value = "DELETE FROM email_verifiers WHERE user_id = :userId", nativeQuery = true)
    void deleteByUserId(@Param("userId") Long userId);
}
