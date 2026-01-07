package com.farukkaraman.springbootblogwithquerydsl.controller;

import com.farukkaraman.springbootblogwithquerydsl.dto.auth.AuthResponseDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.auth.LoginRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.user.UserRegisterRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.security.CustomUserDetails;
import com.farukkaraman.springbootblogwithquerydsl.security.jwt.JwtTokenProvider;
import com.farukkaraman.springbootblogwithquerydsl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(userDetails);

        return ResponseEntity.ok(AuthResponseDto.of(
                token,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getName()));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequestDto request) {
        userService.registerUser(request);
        return ResponseEntity.ok("Kullanıcı başarıyla kaydedildi!");
    }
}
