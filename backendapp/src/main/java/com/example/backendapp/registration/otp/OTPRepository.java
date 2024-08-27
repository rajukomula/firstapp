package com.example.backendapp.registration.otp;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByOtpValue(int otpValue);

    void delete(OTP otp);
}
