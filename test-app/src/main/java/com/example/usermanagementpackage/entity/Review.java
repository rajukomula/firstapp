package com.example.usermanagementpackage.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Long reviewerId;    // Only stores reviewer ID
    private String reviewerName; // Only stores reviewer name
    private String reviewerAvatar; // Only stores reviewer avatar

    private int rating;
    private String comment;
    private String date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // The user receiving the review

    // ✅ Constructors
    public Review() {}

    public Review(Long reviewerId, String reviewerName, String reviewerAvatar, int rating, String comment, String date, User user) {
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.reviewerAvatar = reviewerAvatar;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.user = user;
    }

    // ✅ Getters and Setters
    public Long getReviewId() { return reviewId; }
    public Long getReviewerId() { return reviewerId; }
    public void setReviewerId(Long reviewerId) { this.reviewerId = reviewerId; }

    public String getReviewerName() { return reviewerName; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }

    public String getReviewerAvatar() { return reviewerAvatar; }
    public void setReviewerAvatar(String reviewerAvatar) { this.reviewerAvatar = reviewerAvatar; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
