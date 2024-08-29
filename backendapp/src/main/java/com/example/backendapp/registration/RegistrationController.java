package com.example.backendapp.registration;

import com.example.backendapp.appuser.AppUser;
import com.example.backendapp.appuser.AppUserService;
import com.example.backendapp.exceptions.EmailAlreadyTakenException;
import com.example.backendapp.exceptions.OtpNotSentException;
import com.example.backendapp.exceptions.IncorrectOtpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    private final AppUserService appUserService;

    public RegistrationController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AppUser appUser) {
        try {
            String response = appUserService.signUpUser(appUser);
            return ResponseEntity.ok(response);
        } catch (EmailAlreadyTakenException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (OtpNotSentException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestParam String email, @RequestParam int otpValue) {
        try {
            appUserService.verifyOtp(email, otpValue);
            return ResponseEntity.ok("User verified successfully.");
        } catch (IncorrectOtpException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestParam String email, @RequestParam String password) {
        try {
            String token = appUserService.signInUser(email, password);
            return ResponseEntity.ok("Sign-in successful. Confirmation token: " + token);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials: " + e.getMessage());
        }
    }
}
