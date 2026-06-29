package com.pageturn.service;

import com.pageturn.dto.AdminStatsResponse;
import com.pageturn.entity.Review;
import com.pageturn.repository.BookRepository;
import com.pageturn.repository.ReviewRepository;
import com.pageturn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public AdminStatsResponse getStats() {
        Map<String, Long> reviewsPerLanguage = bookRepository.findAll().stream()
                .filter(b -> b.getLanguage() != null)
                .collect(Collectors.groupingBy(
                        b -> b.getLanguage(),
                        Collectors.summingLong(b -> b.getReviewCount() != null ? b.getReviewCount() : 0)
                ));

        return AdminStatsResponse.builder()
                .totalBooks(bookRepository.count())
                .totalReviews(reviewRepository.count())
                .pendingModeration(reviewRepository.countByStatus(Review.Status.PENDING))
                .activeUsers(userRepository.count())
                .reviewsPerLanguage(reviewsPerLanguage)
                .build();
    }
}
