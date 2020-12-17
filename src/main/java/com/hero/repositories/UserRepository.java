package com.hero.repositories;

import com.hero.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u join fetch u.authorities a where u.username=:username")
    User findByUsername(@Param("username") String username);

    @Query("select u from User u join fetch u.authorities a where u.email=:email")
    User findByEmail(@Param("email") String email);

    @Query(value = "DELETE FROM users WHERE id = :id;" +
            "DELETE FROM email_verifier WHERE user_id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);
}
