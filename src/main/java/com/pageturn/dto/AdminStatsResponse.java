package com.pageturn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AdminStatsResponse {
    private long totalBooks;
    private long totalReviews;
    private long pendingModeration;
    private long activeUsers;
    private Map<String, Long> reviewsPerLanguage;
}
