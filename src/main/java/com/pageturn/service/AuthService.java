package com.pageturn.service;

import com.pageturn.config.JwtUtil;
import com.pageturn.dto.AuthResponse;
import com.pageturn.dto.LoginRequest;
import com.pageturn.dto.UserRequest;
import com.pageturn.entity.User;
import com.pageturn.exception.ResourceNotFoundException;
import com.pageturn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("No account found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return toResponse(user, "Login successful");
    }

    public AuthResponse register(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + request.getEmail());
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : User.Role.USER)
                .avatarUrl(request.getAvatarUrl())
                .bio(request.getBio())
                .joinedDate(LocalDate.now())
                .build();

        User saved = userRepository.save(user);
        return toResponse(saved, "Registration successful");
    }

    private AuthResponse toResponse(User user, String message) {
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .avatarUrl(user.getAvatarUrl())
                .bio(user.getBio())
                .joinedDate(user.getJoinedDate())
                .token(token)
                .message(message)
                .build();
    }
}
