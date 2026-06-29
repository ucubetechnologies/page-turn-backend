package com.pageturn.service;

import com.pageturn.dto.ReviewRequest;
import com.pageturn.entity.Book;
import com.pageturn.entity.Review;
import com.pageturn.entity.User;
import com.pageturn.exception.ResourceNotFoundException;
import com.pageturn.repository.BookRepository;
import com.pageturn.repository.ReviewRepository;
import com.pageturn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookService bookService;

    public List<Review> getApprovedReviewsForBook(Long bookId) {
        return reviewRepository.findByBookIdAndStatus(bookId, Review.Status.APPROVED);
    }

    public List<Review> getReviewsByStatus(Review.Status status) {
        return reviewRepository.findByStatus(status);
    }

    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    public Review submitReview(Long bookId, ReviewRequest request) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookId));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getUserId()));

        Review review = Review.builder()
                .book(book)
                .user(user)
                .rating(request.getRating())
                .title(request.getTitle())
                .body(request.getBody())
                .date(LocalDate.now())
                .helpfulCount(0)
                .status(Review.Status.PENDING)
                .build();
        return reviewRepository.save(review);
    }

    public Review markHelpful(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found: " + reviewId));
        review.setHelpfulCount(review.getHelpfulCount() + 1);
        return reviewRepository.save(review);
    }

    public Review updateStatus(Long reviewId, Review.Status status) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found: " + reviewId));
        review.setStatus(status);
        Review saved = reviewRepository.save(review);
        bookService.updateBookRating(review.getBook().getId());
        return saved;
    }
}
