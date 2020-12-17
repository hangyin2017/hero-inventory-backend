package com.hero.services;

import com.google.common.base.Strings;
import com.hero.dtos.user.UserGetDto;
import com.hero.dtos.user.UserPostDto;
import com.hero.entities.EmailVerifier;
import com.hero.entities.User;
import com.hero.jwt.JwtConfig;
import com.hero.jwt.JwtUtility;
import com.hero.mappers.UserMapper;
import com.hero.repositories.AuthorityRepository;
import com.hero.repositories.UserRepository;
import com.hero.security.PasswordConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${application.email.signUpTokenExpirationAfterMinutes}")
    private int signUpTokenExpirationAfterMinutes;

    @Value("${application.email.resetPasswordTokenExpirationAfterMinutes}")
    private int resetPasswordTokenExpirationAfterMinutes;

    private final JwtConfig jwtConfig;
    private final JwtUtility jwtUtility;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthorityRepository authorityRepository;
    private final PasswordConfig passwordConfig;
    private final int USERNAME_MIN_LENGTH = 6;
    private final int PASSWORD_MIN_LENGTH = 8;

    public void checkUsername(String username) {
        if (username == null || username.length() < USERNAME_MIN_LENGTH) {
            throw new RuntimeException("Username cannot be less than " + USERNAME_MIN_LENGTH + " characters");
        }

        if (userRepository.findByUsername(username) != null) { throw new RuntimeException("Username already exists"); }
    }

    public void checkEmail(String email) {
        if (email == null || email.length() == 0) {
            throw new RuntimeException("Please input a valid email address");
        }

        if (userRepository.findByEmail(email) != null) { throw new RuntimeException("This email has been used"); }
    }

    public void checkPassword(String password) {
        if (password == null || password.length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("Password cannot be less than " + PASSWORD_MIN_LENGTH + " characters");
        }
    }

    private Claims readJwsBody(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtConfig.secretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired");
        }
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    public User findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) { throw new RuntimeException("User does not exist"); }
        return user;
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) { throw new RuntimeException("User does not exist"); }
        return user;
    }

    public UserGetDto getByHeaderWithToken(HttpHeaders headers) {
        List<String> authorizationHeaderList = headers.get(jwtConfig.getAuthorizationHeader());

        if (authorizationHeaderList == null || authorizationHeaderList.get(0) == null) {
            throw new RuntimeException("Invalid token");
        }

        String token = getTokenFromHeader(authorizationHeaderList.get(0));

        return getByToken(token);
    }

    public String getTokenFromHeader(String authorizationHeader) {
        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            throw new RuntimeException("Invalid token");
        }

        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");

        return token;
    }

    public UserGetDto getByToken(String token) {
        String username = readJwsBody(token).getSubject();
        return userMapper.fromEntity(userRepository.findByUsername(username));
    }

    public List<UserGetDto> getAll() {
        return userRepository.findAll().stream()
                .map((user -> userMapper.fromEntity(user)))
                .collect(Collectors.toList());
    }

    public UserGetDto getOne(Long id) {
        return userMapper.fromEntity(findUserById(id));
    }

    @Transactional
    public UserGetDto addOne(UserPostDto userPostDto) {
        String username = userPostDto.getUsername();
        String password = userPostDto.getPassword();
        String email = userPostDto.getEmail();

        checkUsername(username);
        checkEmail(email);
        checkPassword(password);

        User user = userMapper.toEntity(userPostDto);

        user.setEncodedPassword(passwordConfig.passwordEncoder().encode(password));
        user.setStatus("unverified");
        user.setAuthorities(Set.of(authorityRepository.findByPermission("ROLE_TRAINEE")));
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getId();

        String token = jwtUtility.createTokenWithExpirationTime(userId.toString(), signUpTokenExpirationAfterMinutes);

        emailService.addEmailVerifier(userId, email, token);
        emailService.sendSignUpVerificationEmail(userId);

        return userMapper.fromEntity(savedUser);
    }

    @Transactional
    public void delete(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);

        EmailVerifier emailVerifier = emailService.getEmailVerifierByUserId(id);
        if (emailVerifier != null) {
            emailService.deleteEmailVerifier(emailVerifier);
        }
    }

    @Transactional
    public UserGetDto verifyEmail(String token) {
        Long userId = Long.parseLong(readJwsBody(token).getSubject());
        User user = findUserById(userId);
        EmailVerifier emailVerifier = emailService.getEmailVerifierByToken(token);

        user.setStatus("verified");
        user = userRepository.save(user);
        emailService.deleteEmailVerifier(emailVerifier);

        return userMapper.fromEntity(user);
    }

    public void forgetPassword(String email) {
        User user = findUserByEmail(email);
        String token = jwtUtility.createTokenWithExpirationTime(user.getId().toString(),
                resetPasswordTokenExpirationAfterMinutes);

        emailService.addEmailVerifier(user.getId(), email, token);
        emailService.sendResetPasswordVerificationEmail(user.getId());
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        Long userId = Long.parseLong(readJwsBody(token).getSubject());
        User user = findUserById(userId);
        EmailVerifier emailVerifier = emailService.getEmailVerifierByToken(token);

        user.setEncodedPassword(passwordConfig.passwordEncoder().encode(newPassword));
        userRepository.save(user);
        emailService.deleteEmailVerifier(emailVerifier);
    }
 }
