package com.pageturn.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookRequest {
    @NotBlank private String title;
    @NotBlank private String author;
    private String language;
    private String genre;
    private String coverUrl;
    private String description;
    private String publisher;
    private Integer year;
    private Integer pages;
    private Boolean isFeatured = false;
    private Boolean isBestseller = false;
}
