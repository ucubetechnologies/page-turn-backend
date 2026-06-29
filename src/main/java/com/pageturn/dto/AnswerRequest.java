package com.pageturn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnswerRequest {
    @NotBlank
    private String body;

    @NotNull
    private Long userId;
}
