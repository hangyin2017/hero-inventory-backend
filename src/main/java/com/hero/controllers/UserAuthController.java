package com.hero.controllers;

import com.hero.dtos.user.UserGetDto;
import com.hero.dtos.user.UserPostDto;
import com.hero.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("auth")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserGetDto> getByToken(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(userService.getByHeaderWithToken(headers));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserGetDto>> getAll() {
        List<UserGetDto> userGetDtoList = userService.getAll();
        return ResponseEntity.ok(userGetDtoList);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserGetDto> getById(@PathVariable Long id) {
        UserGetDto userGetDto = userService.getOne(id);
        return ResponseEntity.ok(userGetDto);
    }

    @PostMapping("/sign_up")
    public ResponseEntity<UserGetDto> register(@RequestBody UserPostDto userPostDto) {
        return ResponseEntity.ok(userService.addOne(userPostDto));
    }

    @PostMapping("/sign_up/username")
    public ResponseEntity<?> checkUsername(@RequestBody String username) {
        userService.checkUsername(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign_up/email")
    public ResponseEntity<?> checkEmail(@RequestBody String email) {
        userService.checkEmail(email);
        return ResponseEntity.ok().build();
    }
}
