package com.example.usermanagementpackage.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewer;
    private int rating;
    private String comment;
    private String date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ✅ Constructors
    public Review() {}

    public Review(String reviewer, int rating, String comment, String date, User user) {
        this.reviewer = reviewer;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.user = user;
    }

    // ✅ Getters and Setters
    public Long getId() { return id; }
    public String getReviewer() { return reviewer; }
    public void setReviewer(String reviewer) { this.reviewer = reviewer; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
