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

    @Autowired
    private ReviewRepository reviewRepository;    
    
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private WorkSampleRepository workSampleRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

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

    public User addSkill(Long userId, Skill skill) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        skill.setUser(user);
        skillRepository.save(skill);
        return user;
    }

    public User updateSkill(Long userId, Long skillId, String newSkillName) {
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"));
        skill.setName(newSkillName);
        skillRepository.save(skill);
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User removeSkill(Long userId, Long skillId) {
        if (!skillRepository.existsById(skillId)) {
            throw new RuntimeException("Skill not found");
        }
        skillRepository.deleteById(skillId);
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }
        /* ========================= REVIEWS ========================= */

        public void addReview(Long userId, Review review) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                review.setUser(user);
                reviewRepository.save(review);
            } else {
                throw new RuntimeException("User not found");
            }
        }
    
        public void updateReview(Long reviewId, String newReviewer, int newRating, String newComment, String newDate) {
            Optional<Review> optionalReview = reviewRepository.findById(reviewId);
            if (optionalReview.isPresent()) {
                Review review = optionalReview.get();
                review.setReviewer(newReviewer);
                review.setRating(newRating);
                review.setComment(newComment);
                review.setDate(newDate);
                reviewRepository.save(review);
            } else {
                throw new RuntimeException("Review not found");
            }
        }
    
        public void removeReview(Long reviewId) {
            if (reviewRepository.existsById(reviewId)) {
                reviewRepository.deleteById(reviewId);
            } else {
                throw new RuntimeException("Review not found");
            }
        }
    

        /* ========================= WORK SAMPLES ========================= */
    
        public void addWorkSample(Long userId, WorkSample workSample) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                workSample.setUser(user);
                workSampleRepository.save(workSample);
            } else {
                throw new RuntimeException("User not found");
            }
        }
    
        public void updateWorkSample(Long sampleId, String newFileUrl, String newFileType) {
            Optional<WorkSample> optionalWorkSample = workSampleRepository.findById(sampleId);
            if (optionalWorkSample.isPresent()) {
                WorkSample workSample = optionalWorkSample.get();
                workSample.setFileUrl(newFileUrl);
                workSample.setFileType(newFileType);
                workSampleRepository.save(workSample);
            } else {
                throw new RuntimeException("Work sample not found");
            }
        }
    
        public void removeWorkSample(Long sampleId) {
            if (workSampleRepository.existsById(sampleId)) {
                workSampleRepository.deleteById(sampleId);
            } else {
                throw new RuntimeException("Work sample not found");
            }
        }
                                          
        /* ========================= AVAILABILITY ========================= */
    
        public void addAvailability(Long userId, Availability availability) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                availability.setUser(user);
                availabilityRepository.save(availability);
            } else {
                throw new RuntimeException("User not found");
            }
        }
    
        public void updateAvailability(Long availabilityId, List<String> newDaysAvailable) {
            Optional<Availability> optionalAvailability = availabilityRepository.findById(availabilityId);
            if (optionalAvailability.isPresent()) {
                Availability availability = optionalAvailability.get();
                availability.setDaysAvailable(newDaysAvailable);
                availabilityRepository.save(availability);
            } else {
                throw new RuntimeException("Availability not found");
            }
        }
    
        public void removeAvailability(Long availabilityId) {
            if (availabilityRepository.existsById(availabilityId)) {
                availabilityRepository.deleteById(availabilityId);
            } else {
                throw new RuntimeException("Availability not found");
            }
        }


    }
    

    // public User updateReviews(Long id, List<String> reviews) {
    //     return userRepository.findById(id).map(user -> {
    //         user.setReviews(reviews);
    //         return userRepository.save(user);
    //     }).orElseThrow(() -> new RuntimeException("User not found"));
    // }

    // public User updateAvailability(Long id, List<String> availability) {
    //     return userRepository.findById(id).map(user -> {
    //         user.setAvailability(availability);
    //         return userRepository.save(user);
    //     }).orElseThrow(() -> new RuntimeException("User not found"));
    // }

    // public User updateWorkSamples(Long id, List<String> workSamples) {
    //     return userRepository.findById(id).map(user -> {
    //         user.setWorkSamples(workSamples);
    //         return userRepository.save(user);
    //     }).orElseThrow(() -> new RuntimeException("User not found"));
    // }




