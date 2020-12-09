package com.hero.controllers;

import com.hero.dtos.user.UserGetDto;
import com.hero.dtos.user.UserPostDto;
import com.hero.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserGetDto>> getAll() {
        List<UserGetDto> userGetDtoList = userService.getAll();
        return ResponseEntity.ok(userGetDtoList);
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserGetDto> addOne(@RequestBody UserPostDto userPostDto) {
        return ResponseEntity.ok(userService.signUp(userPostDto));
    }
}
