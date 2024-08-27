// package com.example.backendapp.appuser;

// import com.example.backendapp.registration.token.ConfirmationToken;
// import com.example.backendapp.registration.token.ConfirmationTokenService;
// import lombok.AllArgsConstructor;
// import lombok.NoArgsConstructor;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;
// import org.springframework.context.annotation.Bean;
// import org.springframework.beans.factory.annotation.Autowired;

// import java.time.LocalDateTime;
// import java.util.UUID;
// import java.util.Date;

// @Service
// @AllArgsConstructor
// public class AppUserService implements UserDetailsService {

      
//     private final static String USER_NOT_FOUND_MSG =
//             "user with email %s not found";

//     private final AppUserRepository appUserRepository;
//     private final BCryptPasswordEncoder bCryptPasswordEncoder;
//     private final ConfirmationTokenService confirmationTokenService;

//     @Override
//     public UserDetails loadUserByUsername(String email)
//             throws UsernameNotFoundException {
//         return appUserRepository.findByEmailIgnoreCase(email)
//                 .orElseThrow(() ->
//                         new UsernameNotFoundException(
//                                 String.format(USER_NOT_FOUND_MSG, email)));
//     }

//     public String signUpUser(AppUser appUser) {
//         boolean userExists = appUserRepository
//                 .findByEmailIgnoreCase(appUser.getEmail())
//                 .isPresent();

//         if (userExists) {
//             // TODO check of attributes are the same and
//             // TODO if email not confirmed send confirmation email.
//             //check if user is enabled
            

//             throw new IllegalStateException("email already taken");
//         }

//         String encodedPassword = bCryptPasswordEncoder
//                 .encode(appUser.getPassword());

//         appUser.setPassword(encodedPassword);

//         appUserRepository.save(appUser);

//         String token = UUID.randomUUID().toString();

//         ConfirmationToken confirmationToken = new ConfirmationToken(
//                 token,
//                 new Date(),
//                 LocalDateTime.now().plusMinutes(15),
//                 appUser
//         );

//         confirmationTokenService.saveConfirmationToken(
//                 confirmationToken);

// //        TODO: SEND EMAIL

//         return token;
//     }


//     public int enableAppUser(String email) {
//         return appUserRepository.enableAppUser(email);
//     }
// }

package com.example.backendapp.appuser;

import com.example.backendapp.registration.otp.OTP;
import com.example.backendapp.registration.otp.OTPRepository;
import com.example.backendapp.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

import java.util.Random;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OTPRepository otpRepository;
    private final EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public AppUser getAppUserByEmail(String email) {
        return appUserRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findByEmailIgnoreCase(appUser.getEmail()).isPresent();

        if (userExists) {
            AppUser existingUser = getAppUserByEmail(appUser.getEmail());
            if (existingUser.getPassword().equals(appUser.getPassword())) {
                if (existingUser.isEnabled()) {
                    return "User already exists and is enabled";
                } else {
                    // Resend OTP
                    OTP existingOtp = existingUser.getOtp();
                    if (existingOtp != null && !existingOtp.isOtpExpired()) {
                        sendOtpEmail(existingUser.getEmail(), existingOtp.getOtpValue());
                    } else {
                        // Generate and send new OTP
                        int otpValue = generateOtpAndSendEmail(existingUser);
                        return "OTP resent";
                    }
                    return "OTP resent";
                }
            } else {
                throw new IllegalStateException("Email already taken but passwords do not match");
            }
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

        // Generate and send OTP
        int otpValue = generateOtpAndSendEmail(appUser);

        return "OTP sent";
    }

    private int generateOtpAndSendEmail(AppUser appUser) {
        // Generate a 6-digit OTP
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);

        // Create and save OTP
        OTP otp = new OTP(otpValue);
        otpRepository.save(otp);

        // Set OTP for the user
        appUser.setOtp(otp);
        appUserRepository.save(appUser);

        // Send OTP via email
        sendOtpEmail(appUser.getEmail(), otpValue);

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

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
