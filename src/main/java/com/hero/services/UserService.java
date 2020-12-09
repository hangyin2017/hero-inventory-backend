package com.hero.services;

import com.hero.dtos.user.UserGetDto;
import com.hero.dtos.user.UserPostDto;
import com.hero.entities.User;
import com.hero.mappers.UserMapper;
import com.hero.repositories.AuthorityRepository;
import com.hero.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthorityRepository authorityRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public List<UserGetDto> getAll() {
        return userRepository.findAll().stream()
                .map((user -> userMapper.fromEntity(user)))
                .collect(Collectors.toList());
    }

    public UserGetDto addOne(UserPostDto userPostDto) {
        String username = userPostDto.getUsername();
        String password = userPostDto.getPassword();
        User user = userMapper.toEntity(userPostDto);

        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }

        user.setEncodedPassword(passwordEncoder.encode(password));
        user.setAuthorities(Set.of(authorityRepository.findByPermission("ROLE_TRAINEE")));

        return userMapper.fromEntity(userRepository.save(user));
    }
}
