package com.pageturn.repository;

import com.pageturn.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookIdAndStatus(Long bookId, Review.Status status);
    List<Review> findByStatus(Review.Status status);
    List<Review> findByUserId(Long userId);
    long countByStatus(Review.Status status);
}
