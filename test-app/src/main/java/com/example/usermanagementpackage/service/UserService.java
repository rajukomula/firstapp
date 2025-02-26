package com.example.usermanagementpackage.service;

import com.example.usermanagementpackage.entity.User;
import com.example.usermanagementpackage.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;    
    
    @Autowired
    private WorkSampleRepository workSampleRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username) // Assuming users are identified by email
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    // ------- User Details Handling -----------

    @Transactional
    public User updateUser(Long userId, Map<String, Object> updates) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    
        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> user.setName((String) value);
                case "title" -> user.setTitle((String) value);
                case "location" -> user.setLocation((String) value);
                case "rating" -> user.setRating(Double.parseDouble(value.toString()));
                case "reviewCount" -> user.setReviewCount(Integer.parseInt(value.toString()));
                case "successRate" -> user.setSuccessRate((String) value);
                case "responseTime" -> user.setResponseTime((String) value);
                case "completedJobs" -> user.setCompletedJobs(Integer.parseInt(value.toString()));
                case "hourlyRate" -> user.setHourlyRate(Double.parseDouble(value.toString()));
                case "dailyRate" -> user.setDailyRate(Double.parseDouble(value.toString()));
                default -> throw new IllegalArgumentException("Invalid field: " + key);
            }
        });
    
        return userRepository.save(user);
    }

    // ✅ Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }   

    // ✅ Get user by ID
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    // ✅ Delete user (removes related data)
    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // Delete user-related data before deleting user
        reviewRepository.deleteByUserId(userId);
        workSampleRepository.deleteByUserId(userId);
        availabilityRepository.deleteByUserId(userId);
        
        userRepository.deleteById(userId);
    }

    // ------- Skill Handling -----------

    // ✅ Get user's skills
    public List<String> getSkills(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return user.getSkills();
    }

    // ✅ Add a new skill to user
    @Transactional
    public User createSkill(Long userId, String skill) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<String> skills = new ArrayList<>(user.getSkills()); // Clone the list to avoid modifying original reference
        if (!skills.contains(skill)) {
            skills.add(skill);
            user.setSkills(skills);
            return userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Skill already exists");
        }
    }
}
