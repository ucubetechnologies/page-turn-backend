package com.pageturn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuestionRequest {
    @NotBlank
    private String questionText;

    @NotNull
    private Long userId;
}
