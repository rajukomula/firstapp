// package com.example.backendapp.registration;

// import lombok.AllArgsConstructor;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.mail.SimpleMailMessage;
// import com.example.backendapp.appuser.AppUser;
// import com.example.backendapp.appuser.AppUserRepository;
// import com.example.backendapp.registration.otp.OTPRepository;
// import com.example.backendapp.registration.otp.OTP;
// import com.example.backendapp.email.EmailService;
// import com.example.backendapp.registration.token.ConfirmationToken;
// import com.example.backendapp.registration.token.ConfirmationTokenRepository;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.web.servlet.ModelAndView;

// import java.util.Random;

// import org.springframework.beans.factory.annotation.Autowired;

// @RestController
// @RequestMapping(path = "api/v1")
// @AllArgsConstructor
// public class RegistrationController {

//     @Autowired
//     private AppUserRepository appUserRepository;

//     @Autowired
//     private ConfirmationTokenRepository confirmationTokenRepository;

//     @Autowired
//     private OTPRepository otpRepository;

//     @Autowired
//     private EmailService emailService;

//     @PostMapping("/register")
//     public void registerUser(@RequestBody AppUser appUser) {
//         AppUser existingUser = appUserRepository.findByEmailIgnoreCase(appUser.getEmail())
//                                                 .orElse(null);

//         if (existingUser != null) {
//             System.out.println("User already exists");
//         } else {
//             // Generate a 6-digit OTP
//             Random random = new Random();
//             int otpValue = 100000 + random.nextInt(900000);

//             // Create and save OTP
//             OTP otp = new OTP(otpValue);
//             otpRepository.save(otp);

//             // Set OTP for the user
//             appUser.setOtp(otp);

//             // Save the user with OTP
//             appUserRepository.save(appUser);

//             // Send OTP via email
//             SimpleMailMessage mailMessage = new SimpleMailMessage();
//             mailMessage.setTo(appUser.getEmail());
//             mailMessage.setSubject("Complete Registration with OTP");
//             mailMessage.setFrom("testemailforapp9@gmail.com");
//             mailMessage.setText("Your OTP for account confirmation is: " + otpValue);
//             emailService.sendEmail(mailMessage);

//             System.out.println("OTP sent to user, awaiting verification");
//         }
//     }

//     @PostMapping("/verify-otp")
//     public String verifyOTP(@RequestParam String email, @RequestParam int otpValue) {
//         System.out.println("verifyotp entered");
//         System.out.println(email);

//         // Fetch the user by email
//         AppUser appUser = appUserRepository.findByEmailIgnoreCase(email)
//                                            .orElse(null);

//         // Check if the user was found
//         if (appUser == null) {
//             System.out.println("User not found");
//             return "User not found";
//         }

//         // Check if OTP exists for the user
//         OTP otp = appUser.getOtp();
//         System.out.println("otpValue is :  " + appUser.getOtpValue().toString());
//         System.out.println(appUser);

//         if (otp == null) {
//             System.out.println("OTP not found");
//             return "OTP not found";
//         }

//         otp.checkIfExpired();

//         if (otp.isOtpExpired()) {
//             System.out.println("OTP has expired");
//             return "OTP has expired";
//         } else {
//             if (otp.getOtpValue() == otpValue) {
//                 System.out.println("OTP verified");

//                 // OTP is correct and user is verified
//                 appUser.setEnabled(true);
//                 appUserRepository.save(appUser); // Save the user only after OTP verification
//                 otpRepository.delete(otp); // Remove OTP after successful verification
//                 System.out.println("OTP verification complete");

//                 return "User verified successfully";
//             } else {
//                 return "Invalid OTP";
//             }
//         }
//     }
// }

package com.example.backendapp.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.backendapp.appuser.AppUser;
import com.example.backendapp.appuser.AppUserService;
import com.example.backendapp.registration.otp.OTP;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class RegistrationController {

    private final AppUserService appUserService;

    @PostMapping("/register")
    public String registerUser(@RequestBody AppUser appUser) {
        return appUserService.signUpUser(appUser);
    }

    @PostMapping("/verify-otp")
    public String verifyOTP(@RequestParam String email, @RequestParam int otpValue) {
        AppUser appUser = appUserService.getAppUserByEmail(email);

        if (appUser == null) {
            return "User not found";
        }

        OTP otp = appUser.getOtp();

        if (otp == null) {
            return "OTP not found";
        }

        otp.checkIfExpired();

        if (otp.isOtpExpired()) {
            return "OTP has expired";
        } else {
            if (otp.getOtpValue() == otpValue) {
                appUser.setEnabled(true);
                appUserService.enableAppUser(email);
                return "User verified successfully";
            } else {
                return "Invalid OTP";
            }
        }
    }
}
