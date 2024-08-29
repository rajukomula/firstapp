package com.example.backendapp.registration.otp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int otpValue;
    private LocalDateTime createdAt;
    private boolean otpExpired;

    public OTP() {
        // No-argument constructor
    }

    public OTP(int otpValue) {
        this.otpValue = otpValue;
        this.createdAt = LocalDateTime.now();
        this.otpExpired = false;
    }

    // Method to check if the OTP has expired and update its status
    public void checkIfExpired() {
        if (LocalDateTime.now().isAfter(this.createdAt.plusMinutes(5))) {
            this.otpExpired = true;
        }
    }

    // Automatically update expiration status when OTP is accessed
    public boolean isOtpExpired() {
        checkIfExpired();
        return otpExpired;
    }

    // Getters and setters
    public int getOtpValue() {
        return otpValue;
    }

    public void setOtpValue(int otpValue) {
        this.otpValue = otpValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setOtpExpired(boolean otpExpired) {
        this.otpExpired = otpExpired;
    }
}
