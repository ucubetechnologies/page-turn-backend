package com.pageturn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "books")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private String language;
    private String genre;
    private String coverUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String publisher;
    private Integer year;
    private Integer pages;

    private Double rating = 0.0;
    private Integer reviewCount = 0;

    private Boolean isFeatured = false;
    private Boolean isBestseller = false;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Question> questions;
}
