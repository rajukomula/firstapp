package com.example.usermanagementpackage.controller;

import com.example.usermanagementpackage.entity.Review;
import com.example.usermanagementpackage.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // ✅ Create Review API
    @PostMapping("/{userId}/reviews/{reviewerId}")
    public ResponseEntity<Review> createReview(
            @PathVariable Long userId,
            @PathVariable Long reviewerId,
            @RequestBody Review reviewData) {

        Review newReview = reviewService.createReview(userId, reviewerId, reviewData);
        return ResponseEntity.status(201).body(newReview);
    }

    // ✅ Delete Review API
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    // ✅ Get All Reviews for a User API
    @GetMapping("/{userId}/reviews")
    public ResponseEntity<List<Review>> getReviews(@PathVariable Long userId) {
        List<Review> reviews = reviewService.getReviews(userId);
        return ResponseEntity.ok(reviews);
    }
}
