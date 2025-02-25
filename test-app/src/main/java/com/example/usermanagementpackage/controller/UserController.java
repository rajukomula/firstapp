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
}
