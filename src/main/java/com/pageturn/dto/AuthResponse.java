package com.pageturn.dto;

import com.pageturn.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String name;
    private String email;
    private User.Role role;
    private String avatarUrl;
    private String bio;
    private LocalDate joinedDate;
    private String message;
}
