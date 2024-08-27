package com.example.backendapp.registration.otp;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.Duration;

@Entity
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int otpValue;

    private LocalDateTime createdAt;

    private boolean otpExpired;

    public OTP(int otpValue) {
        this.otpValue = otpValue;
        this.createdAt = LocalDateTime.now();
        this.otpExpired = false;
    }

    // Method to check if the OTP has expired
    public void checkIfExpired() {
        if (Duration.between(this.createdAt, LocalDateTime.now()).toMinutes() >= 5) {
            this.otpExpired = true;
        }
    }

    // Getters and setters
    public boolean isOtpExpired() {
        return otpExpired;
    }

    public int getOtpValue() {
        return otpValue;
    }
}