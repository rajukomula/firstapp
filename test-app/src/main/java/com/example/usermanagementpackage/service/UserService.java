package com.example.usermanagementpackage.service;

import com.example.usermanagementpackage.entity.*;
import com.example.usermanagementpackage.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    // ✅ Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // ✅ Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // ✅ Delete a user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // ✅ Update Individual Attributes
    public User updateAvatar(Long id, String avatar) {
        return userRepository.findById(id).map(user -> {
            user.setAvatar(avatar);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateName(Long id, String name) {
        return userRepository.findById(id).map(user -> {
            user.setName(name);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateTitle(Long id, String title) {
        return userRepository.findById(id).map(user -> {
            user.setTitle(title);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateLocation(Long id, String location) {
        return userRepository.findById(id).map(user -> {
            user.setLocation(location);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateRating(Long id, double rating) {
        return userRepository.findById(id).map(user -> {
            user.setRating(rating);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateReviewCount(Long id, int reviewCount) {
        return userRepository.findById(id).map(user -> {
            user.setReviewCount(reviewCount);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateSuccessRate(Long id, String successRate) {
        return userRepository.findById(id).map(user -> {
            user.setSuccessRate(successRate);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateResponseTime(Long id, String responseTime) {
        return userRepository.findById(id).map(user -> {
            user.setResponseTime(responseTime);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateCompletedJobs(Long id, int completedJobs) {
        return userRepository.findById(id).map(user -> {
            user.setCompletedJobs(completedJobs);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateHourlyRate(Long id, double hourlyRate) {
        return userRepository.findById(id).map(user -> {
            user.setHourlyRate(hourlyRate);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateDailyRate(Long id, double dailyRate) {
        return userRepository.findById(id).map(user -> {
            user.setDailyRate(dailyRate);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateSkills(Long id, List<String> skills) {
        return userRepository.findById(id).map(user -> {
            user.setSkills(skills);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateReviews(Long id, List<String> reviews) {
        return userRepository.findById(id).map(user -> {
            user.setReviews(reviews);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateAvailability(Long id, List<String> availability) {
        return userRepository.findById(id).map(user -> {
            user.setAvailability(availability);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateWorkSamples(Long id, List<String> workSamples) {
        return userRepository.findById(id).map(user -> {
            user.setWorkSamples(workSamples);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
