package com.pageturn.controller;

import com.pageturn.dto.ReviewRequest;
import com.pageturn.entity.Review;
import com.pageturn.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/api/books/{bookId}/reviews")
    public ResponseEntity<List<Review>> getBookReviews(@PathVariable Long bookId) {
        return ResponseEntity.ok(reviewService.getApprovedReviewsForBook(bookId));
    }

    @PostMapping("/api/books/{bookId}/reviews")
    public ResponseEntity<Review> submitReview(@PathVariable Long bookId,
                                               @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.submitReview(bookId, request));
    }

    @PutMapping("/api/reviews/{reviewId}/helpful")
    public ResponseEntity<Review> markHelpful(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.markHelpful(reviewId));
    }

    @PutMapping("/api/reviews/{reviewId}/status")
    public ResponseEntity<Review> updateStatus(@PathVariable Long reviewId,
                                               @RequestBody Map<String, String> body) {
        Review.Status status = Review.Status.valueOf(body.get("status").toUpperCase());
        return ResponseEntity.ok(reviewService.updateStatus(reviewId, status));
    }

    @GetMapping("/api/reviews")
    public ResponseEntity<List<Review>> getReviewsByStatus(
            @RequestParam(defaultValue = "PENDING") String status) {
        return ResponseEntity.ok(reviewService.getReviewsByStatus(Review.Status.valueOf(status.toUpperCase())));
    }

    @GetMapping("/api/users/{userId}/reviews")
    public ResponseEntity<List<Review>> getUserReviews(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }
}
