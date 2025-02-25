package com.example.usermanagementpackage.controller;


import com.example.usermanagementpackage.entity.*;
import com.example.usermanagementpackage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    // ✅ Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Update Basic User Details
    @PatchMapping("/{id}/avatar")
    public ResponseEntity<User> updateAvatar(@PathVariable Long id, @RequestBody String avatarUrl) {
        return ResponseEntity.ok(userService.updateAvatar(id, avatarUrl));
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<User> updateName(@PathVariable Long id, @RequestBody String name) {
        return ResponseEntity.ok(userService.updateName(id, name));
    }

    @PatchMapping("/{id}/title")
    public ResponseEntity<User> updateTitle(@PathVariable Long id, @RequestBody String title) {
        return ResponseEntity.ok(userService.updateTitle(id, title));
    }

    @PatchMapping("/{id}/location")
    public ResponseEntity<User> updateLocation(@PathVariable Long id, @RequestBody String location) {
        return ResponseEntity.ok(userService.updateLocation(id, location));
    }

    @PatchMapping("/{id}/rating")
    public ResponseEntity<User> updateRating(@PathVariable Long id, @RequestBody double rating) {
        return ResponseEntity.ok(userService.updateRating(id, rating));
    }

    @PatchMapping("/{id}/review-count")
    public ResponseEntity<User> updateReviewCount(@PathVariable Long id, @RequestBody int reviewCount) {
        return ResponseEntity.ok(userService.updateReviewCount(id, reviewCount));
    }

    @PatchMapping("/{id}/success-rate")
    public ResponseEntity<User> updateSuccessRate(@PathVariable Long id, @RequestBody String successRate) {
        return ResponseEntity.ok(userService.updateSuccessRate(id, successRate));
    }

    @PatchMapping("/{id}/response-time")
    public ResponseEntity<User> updateResponseTime(@PathVariable Long id, @RequestBody String responseTime) {
        return ResponseEntity.ok(userService.updateResponseTime(id, responseTime));
    }

    @PatchMapping("/{id}/completed-jobs")
    public ResponseEntity<User> updateCompletedJobs(@PathVariable Long id, @RequestBody int completedJobs) {
        return ResponseEntity.ok(userService.updateCompletedJobs(id, completedJobs));
    }

    @PatchMapping("/{id}/hourly-rate")
    public ResponseEntity<User> updateHourlyRate(@PathVariable Long id, @RequestBody double hourlyRate) {
        return ResponseEntity.ok(userService.updateHourlyRate(id, hourlyRate));
    }

    @PatchMapping("/{id}/daily-rate")
    public ResponseEntity<User> updateDailyRate(@PathVariable Long id, @RequestBody double dailyRate) {
        return ResponseEntity.ok(userService.updateDailyRate(id, dailyRate));
    }

    // ✅ Skills Endpoints
    @PostMapping("/{id}/skills")
    public ResponseEntity<User> addSkill(@PathVariable Long id, @RequestBody Skill skill) {
        return ResponseEntity.ok(userService.addSkill(id, skill));
    }

    @PutMapping("/skills/{skillId}")
    public ResponseEntity<User> updateSkill(@PathVariable Long skillId, @RequestBody String newSkillName) {
        return ResponseEntity.ok(userService.updateSkill(skillId, newSkillName));
    }

    @DeleteMapping("/skills/{skillId}")
    public ResponseEntity<Void> removeSkill(@PathVariable Long skillId) {
        userService.removeSkill(skillId);
        return ResponseEntity.noContent().build();
    }

    // ✅ Reviews Endpoints
    @PostMapping("/{id}/reviews")
    public ResponseEntity<User> addReview(@PathVariable Long id, @RequestBody Review review) {
        return ResponseEntity.ok(userService.addReview(id, review));
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<User> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
        return ResponseEntity.ok(userService.updateReview(reviewId, review.getComment(), review.getRating()));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> removeReview(@PathVariable Long reviewId) {
        userService.removeReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    // ✅ Work Samples Endpoints
    @PostMapping("/{id}/worksamples")
    public ResponseEntity<User> addWorkSample(@PathVariable Long id, @RequestBody WorkSample workSample) {
        return ResponseEntity.ok(userService.addWorkSample(id, workSample));
    }

    @PutMapping("/worksamples/{workSampleId}")
    public ResponseEntity<User> updateWorkSample(@PathVariable Long workSampleId, @RequestBody String newFileUrl) {
        return ResponseEntity.ok(userService.updateWorkSample(workSampleId, newFileUrl));
    }

    @DeleteMapping("/worksamples/{workSampleId}")
    public ResponseEntity<Void> removeWorkSample(@PathVariable Long workSampleId) {
        userService.removeWorkSample(workSampleId);
        return ResponseEntity.noContent().build();
    }

    // ✅ Availability Endpoints
    @PostMapping("/{id}/availability")
    public ResponseEntity<User> addAvailability(@PathVariable Long id, @RequestBody Availability availability) {
        return ResponseEntity.ok(userService.addAvailability(id, availability));
    }

    @PutMapping("/availability/{availabilityId}")
    public ResponseEntity<User> updateAvailability(@PathVariable Long availabilityId, @RequestBody String newAvailability) {
        return ResponseEntity.ok(userService.updateAvailability(availabilityId, newAvailability));
    }

    @DeleteMapping("/availability/{availabilityId}")
    public ResponseEntity<Void> removeAvailability(@PathVariable Long availabilityId) {
        userService.removeAvailability(availabilityId);
        return ResponseEntity.noContent().build();
    }
}
