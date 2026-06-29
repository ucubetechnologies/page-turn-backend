package com.pageturn.dto;

import com.pageturn.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank private String name;
    @Email @NotBlank private String email;
    @NotBlank private String password;
    private User.Role role = User.Role.USER;
    private String avatarUrl;
    private String bio;
}
