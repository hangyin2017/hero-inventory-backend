package com.hero.controllers;

import com.hero.dtos.user.UserGetDto;
import com.hero.dtos.user.UserPostDto;
import com.hero.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class UserController {

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

    @GetMapping("/users/filter")
    public ResponseEntity<List<UserGetDto>> filter(@RequestParam String searchInput) {
        return ResponseEntity.ok(userService.findByNameLike(searchInput));
    }

    @PostMapping("/sign_up")
    public ResponseEntity<UserGetDto> register(@RequestBody UserPostDto userPostDto) {
        return ResponseEntity.ok(userService.addOne(userPostDto));
    }

    @PostMapping("/sign_up/username")
    public ResponseEntity<?> checkUsername(@RequestBody Map<String, String> requestMap) {
        userService.checkUsername(requestMap.get("username"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign_up/email")
    public ResponseEntity<?> checkEmail(@RequestBody Map<String, String> requestMap) {
        userService.checkEmail(requestMap.get("email"));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/email_verification")
    public ResponseEntity<UserGetDto> verifyEmail(@RequestParam String token) {
        return ResponseEntity.ok(userService.verifyEmail(token));
    }

    @PostMapping("/forget_password")
    public ResponseEntity<?> forgetPassword(@RequestBody Map<String, String> requestMap) {
        userService.forgetPassword(requestMap.get("email"));
        return ResponseEntity.ok("Reset password link has been sent to your email");
    }

    @PutMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestBody Map<String, String> requestMap) {
        userService.resetPassword(token, requestMap.get("password"));
        return ResponseEntity.ok("Successfully reset password");
    }
}
