package com.example.usermanagementpackage.controller;

import com.example.usermanagementpackage.entity.User;
import com.example.usermanagementpackage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ Update user fields (non-array attributes)
    @PatchMapping("/updateuser/{userId}")
    public ResponseEntity<User> updateUserFields(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> updates) {
        
        User updatedUser = userService.updateUser(userId, updates);
        return ResponseEntity.ok(updatedUser);
    }
    // API Input format: { "name": "John Doe", "location": "New York" }

    // ✅ Create user
    @PostMapping("/createuser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
    /* API Input format:
        {
         "name": "John Doe",
         "title": "Software Engineer",
         "location": "New York",
         "rating": 4.8,
         "reviewCount": 10,
         "successRate": "98%",
         "responseTime": "2 hours",
         "completedJobs": 15,
         "hourlyRate": 50.0,
         "dailyRate": 400.0,
         "skills": ["Java", "Spring Boot"],
         "availability": ["Monday", "Wednesday"],
         "reviews": [],
         "workSamples": []
        }
     */

    // ✅ Get user by ID
    @GetMapping("/getuser/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);         
    }

    // ✅ Delete user
    @DeleteMapping("/deleteuser/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    // ------ Skills Endpoints ------

    // ✅ Get user's skills
    @GetMapping("/getskills/{userId}")
    public ResponseEntity<List<String>> getSkills(@PathVariable Long userId) {
        List<String> skills = userService.getSkills(userId);
        return ResponseEntity.ok(skills);
    }

    // API Input format: { "skill": "Java" }
    @PostMapping("/createskill/{userId}")
    public ResponseEntity<User> createSkill(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {

        String skill = request.get("skill");
        User updatedUser = userService.createSkill(userId, skill);
        return ResponseEntity.ok(updatedUser);
    }
}
