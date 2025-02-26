package com.example.usermanagementpackage.service;

import com.example.usermanagementpackage.entity.Review;
import com.example.usermanagementpackage.entity.User;
import com.example.usermanagementpackage.repository.ReviewRepository;
import com.example.usermanagementpackage.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    // ✅ Create a review
    @Transactional
    public Review createReview(Long userId, Long reviewerId, Review reviewData) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User receiving review not found"));

        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));

        Review review = new Review(
                reviewer.getId(), 
                reviewer.getName(), 
                reviewer.getAvatar(),
                reviewData.getRating(),
                reviewData.getComment(),
                reviewData.getDate(),
                user
        );

        return reviewRepository.save(review);
    }

    // ✅ Delete a review
    @Transactional
    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new RuntimeException("Review not found");
        }
        reviewRepository.deleteById(reviewId);
    }

    // ✅ Get all reviews received by a user
    public List<Review> getReviews(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
}
