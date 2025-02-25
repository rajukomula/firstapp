package com.example.usermanagementpackage.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String avatar;
    private String name;
    private String title;
    private String location;
    private double rating;
    private int reviewCount;
    private String successRate;
    private String responseTime;
    private int completedJobs;
    private double hourlyRate;
    private double dailyRate;

    @ElementCollection
    private List<String> skills;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkSample> workSamples;

    @ElementCollection
    private List<String> availability;

    // ✅ Constructors
    public User() {}

    public User(String avatar, String name, String title, String location, double rating, int reviewCount,
                String successRate, String responseTime, int completedJobs, double hourlyRate, double dailyRate,
                List<String> skills, List<Review> reviews, List<WorkSample> workSamples, List<String> availability) {
        this.avatar = avatar;
        this.name = name;
        this.title = title;
        this.location = location;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.successRate = successRate;
        this.responseTime = responseTime;
        this.completedJobs = completedJobs;
        this.hourlyRate = hourlyRate;
        this.dailyRate = dailyRate;
        this.skills = skills;
        this.reviews = reviews;
        this.workSamples = workSamples;
        this.availability = availability;
    }

    // ✅ Getters and Setters
    public Long getId() { return id; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public int getReviewCount() { return reviewCount; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }
    public String getSuccessRate() { return successRate; }
    public void setSuccessRate(String successRate) { this.successRate = successRate; }
    public String getResponseTime() { return responseTime; }
    public void setResponseTime(String responseTime) { this.responseTime = responseTime; }
    public int getCompletedJobs() { return completedJobs; }
    public void setCompletedJobs(int completedJobs) { this.completedJobs = completedJobs; }
    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
    public double getDailyRate() { return dailyRate; }
    public void setDailyRate(double dailyRate) { this.dailyRate = dailyRate; }
    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    public List<WorkSample> getWorkSamples() { return workSamples; }
    public void setWorkSamples(List<WorkSample> workSamples) { this.workSamples = workSamples; }
    public List<String> getAvailability() { return availability; }
    public void setAvailability(List<String> availability) { this.availability = availability; }
}
