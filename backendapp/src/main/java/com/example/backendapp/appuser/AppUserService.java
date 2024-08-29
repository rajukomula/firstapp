package com.example.backendapp.appuser;

import com.example.backendapp.registration.token.ConfirmationToken;
import com.example.backendapp.registration.token.ConfirmationTokenService;
import com.example.backendapp.registration.otp.OTP;
import com.example.backendapp.registration.otp.OTPRepository;
import com.example.backendapp.email.EmailService;
import com.example.backendapp.exceptions.EmailAlreadyTakenException;
import com.example.backendapp.exceptions.IncorrectOtpException;
import com.example.backendapp.exceptions.OtpNotSentException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

import java.util.Random;
import java.util.UUID;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OTPRepository otpRepository;
    private final EmailService emailService;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public AppUser getAppUserByEmail(String email) {
        return appUserRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }


    public String signInUser(String email, String rawPassword) {
        AppUser appUser = appUserRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));

        // Verify the password
        if (!passwordMatches(appUser.getPassword(), rawPassword)) {
            throw new IllegalStateException("Invalid credentials");
        }

        // Fetch the ConfirmationToken for the user
        Optional<ConfirmationToken> tokenOptional = confirmationTokenService.getTokenByUser(appUser);

        if (tokenOptional.isPresent()) {
            return tokenOptional.get().getConfirmationToken();
        } else {
            throw new IllegalStateException("No confirmation token found for user");
        }
    }

    // Method to match the provided password with the stored encoded password
    private boolean passwordMatches(String encodedPassword, String rawPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }



    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findByEmailIgnoreCase(appUser.getEmail()).isPresent();

        // If user already exists, throw EmailAlreadyTakenException
        if (userExists) {
            throw new EmailAlreadyTakenException("User with this email already exists.");
        }

        // Encrypt and save user
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

        // Generate and save a new confirmation token
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                new Date(),
                appUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // Generate and send OTP
        int otpValue = generateOtpAndSendEmail(appUser);

        return "User created successfully. OTP sent.";
    }

    
    // public String signUpUser(AppUser appUser) {
    //     boolean userExists = appUserRepository.findByEmailIgnoreCase(appUser.getEmail()).isPresent();

    //     if (userExists) {
    //         throw new EmailAlreadyTakenException("User with this email already exists.");
    //     }

    //     String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
    //     appUser.setPassword(encodedPassword);
    //     appUserRepository.save(appUser);

    //     String token = UUID.randomUUID().toString();
    //     ConfirmationToken confirmationToken = new ConfirmationToken(
    //             token,
    //             new Date(),
    //             appUser
    //     );
    //     confirmationTokenService.saveConfirmationToken(confirmationToken);

    //     int otpValue = generateOtpAndSendEmail(appUser);

    //     return "User created successfully. OTP sent.";
    // }

    private int generateOtpAndSendEmail(AppUser appUser) {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);

        OTP otp = new OTP(otpValue);
        otpRepository.save(otp);

        appUser.setOtp(otp);
        appUserRepository.save(appUser);

        try {
            sendOtpEmail(appUser.getEmail(), otpValue);
        } catch (Exception e) {
            throw new OtpNotSentException("Failed to send OTP to email: " + appUser.getEmail());
        }

        return otpValue;
    }

    private void sendOtpEmail(String email, int otpValue) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration with OTP");
        mailMessage.setFrom("testemailforapp9@gmail.com");
        mailMessage.setText("Your OTP for account confirmation is: " + otpValue);
        emailService.sendEmail(mailMessage);
    }

    public void verifyOtp(String email, int otpValue) {
        AppUser appUser = getAppUserByEmail(email);

        OTP otp = appUser.getOtp();
        if (otp == null) {
            throw new IncorrectOtpException("OTP not found.");
        }

        otp.checkIfExpired();
        if (otp.isOtpExpired()) {
            throw new IncorrectOtpException("OTP has expired.");
        }

        if (otp.getOtpValue() != otpValue) {
            throw new IncorrectOtpException("Invalid OTP.");
        }

        // Successfully verified OTP, delete it
        otpRepository.delete(otp);

        appUser.setEnabled(true);
        appUserRepository.save(appUser);
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
