package com.farukkaraman.springbootblogwithquerydsl.controller;

import com.farukkaraman.springbootblogwithquerydsl.dto.user.UserResponseDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.user.UserUpdateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.security.CurrentUser;
import com.farukkaraman.springbootblogwithquerydsl.security.CustomUserDetails;
import com.farukkaraman.springbootblogwithquerydsl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Mevcut kullanıcının bilgilerini getir
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(@CurrentUser CustomUserDetails currentUser) {
        return ResponseEntity.ok(userService.getUserById(currentUser.getId()));
    }

    // Admin: Tüm kullanıcıları listele
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Admin: ID ile kullanıcı getir
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Kullanıcı kendi hesabını güncelleyebilir
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequestDto request,
            @CurrentUser CustomUserDetails currentUser) {
        userService.updateUser(id, request, currentUser.getId());
        return ResponseEntity.ok("Kullanıcı başarıyla güncellendi!");
    }

    // Kullanıcı kendi hesabını silebilir
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id,
            @CurrentUser CustomUserDetails currentUser) {
        userService.deleteUser(id, currentUser.getId());
        return ResponseEntity.ok("Kullanıcı silindi.");
    }
}