package com.farukkaraman.springbootblogwithquerydsl.service;

import com.farukkaraman.springbootblogwithquerydsl.dto.user.UserRegisterRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.user.UserResponseDto;
import com.farukkaraman.springbootblogwithquerydsl.dto.user.UserUpdateRequestDto;
import com.farukkaraman.springbootblogwithquerydsl.entity.AuthProvider;
import com.farukkaraman.springbootblogwithquerydsl.entity.Role;
import com.farukkaraman.springbootblogwithquerydsl.entity.User;
import com.farukkaraman.springbootblogwithquerydsl.mapper.UserMapper;
import com.farukkaraman.springbootblogwithquerydsl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserRegisterRequestDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Bu email zaten kayıtlı!");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setProvider(AuthProvider.LOCAL);

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Transactional
    public void updateUser(Long id, UserUpdateRequestDto request, Long currentUserId) {
        // Kullanıcı sadece kendi hesabını güncelleyebilir
        if (!id.equals(currentUserId)) {
            throw new RuntimeException("Bu işlem için yetkiniz bulunmamaktadır!");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));

        // Email değişiyorsa, yeni email'in başkası tarafından kullanılmadığını kontrol
        // et
        if (request.getEmail() != null && !user.getEmail().equals(request.getEmail())) {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new RuntimeException("Bu email zaten kullanılıyor!");
            }
        }

        userMapper.updateEntityFromDto(request, user);

        // Şifre değişiyorsa encode et
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id, Long currentUserId) {
        // Kullanıcı sadece kendi hesabını silebilir
        if (!id.equals(currentUserId)) {
            throw new RuntimeException("Bu işlem için yetkiniz bulunmamaktadır!");
        }

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Kullanıcı bulunamadı!");
        }
        userRepository.deleteById(id);
    }
}