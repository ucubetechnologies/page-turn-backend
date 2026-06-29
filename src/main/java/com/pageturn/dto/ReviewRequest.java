package com.pageturn.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReviewRequest {
    @Min(1) @Max(5)
    private Integer rating;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @NotNull
    private Long userId;
}
