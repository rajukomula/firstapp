package com.example.backendapp.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import com.example.backendapp.appuser.AppUser;
import com.example.backendapp.appuser.AppUserRepository;
import com.example.backendapp.registration.otp.OTPRepository;
import com.example.backendapp.registration.otp.OTP;
import com.example.backendapp.email.EmailService;
import com.example.backendapp.registration.token.ConfirmationToken;
import com.example.backendapp.registration.token.ConfirmationTokenRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.ModelAndView;
import java.util.Random;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    private OTPRepository otpRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public void registerUser(@RequestBody AppUser appUser) {
        AppUser existingUser = appUserRepository.findByEmailIgnoreCase(appUser.getEmail())
                                                .orElse(null);

        if (existingUser != null) {
            System.out.println("User already exists");
        } else {
            // Generate a 6-digit OTP
            Random random = new Random();
            int otpValue = 100000 + random.nextInt(900000);

            // Create and save OTP
            OTP otp = new OTP(otpValue);
            otpRepository.save(otp);

            // Set OTP for the user but don't save the user yet
            appUser.setOtp(otp);

            // Send OTP via email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(appUser.getEmail());
            mailMessage.setSubject("Complete Registration with OTP");
            mailMessage.setFrom("testemailforapp9@gmail.com");
            mailMessage.setText("Your OTP for account confirmation is: " + otpValue);
            emailService.sendEmail(mailMessage);

            System.out.println("OTP sent to user, awaiting verification");
        }
    }

    @PostMapping("/verify-otp")
    public String verifyOTP(@RequestParam String email, @RequestParam int otpValue) {
        System.out.println("verifyotp entered");
        
        AppUser appUser = appUserRepository.findByEmailIgnoreCase(email)
                                           .orElse(null);
                                           
        System.out.println(appUser);
        

        OTP otp = appUser.getOtp();
        
        otp.checkIfExpired();

        if (otp.isOtpExpired()) {
            System.out.println("main if block entered");
            return "OTP has expired";
        }
        else{
        if (otp.getOtpValue() == otpValue) {
            System.out.println("inner if block entered");

            // OTP is correct and user is verified
            appUser.setEnabled(true);
            appUserRepository.save(appUser); // Save the user only after OTP verification
            otpRepository.delete(otp); // Remove OTP after successful verification
            System.out.println("inner if block exit");

            return "User verified successfully";
        } else {
            return "Invalid OTP";
        }
    }
    }



    @PostMapping("/print")
    public void printStrings(@RequestBody AppUser appUser) {
        // Print the received strings to the console
        System.out.println("First Name: " + appUser.getFirstName());
        System.out.println("Last Name: " + appUser.getLastName());
    }
    
    

    // @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    // public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    // {
    //     ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken)
    //                                         .orElseThrow(() -> new UsernameNotFoundException("ConfirmationToken not found"));

    //     if(token != null)
    //     {
    //     	AppUser user = appUserRepository.findByEmailIgnoreCase(token.getAppUser().getEmail())
    //                         .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    //         user.setEnabled(true);
    //         appUserRepository.save(user);
    //         modelAndView.setViewName("accountVerified");
    //     }
    //     else
    //     {
    //         modelAndView.addObject("message","The link is invalid or broken!");
    //         modelAndView.setViewName("error");
    //     }

    //     return modelAndView;
    // }


    // private AppUserRepository appUserRepository;


    // @Autowired
    // private final RegistrationService registrationService;
    // @Autowired
    // private ConfirmationTokenRepository confirmationTokenRepository;
    // @Autowired
    // private EmailService emailService;


    // @PostMapping("/print")
    // public void printStrings(@RequestBody RegistrationRequest request) {
    //     // Print the received strings to the console
    //     System.out.println("First Name: " + request.getFirstName());
    //     System.out.println("Last Name: " + request.getLastName());
    // }

    // @GetMapping("test")
    // public String test() {
    
    //     return "test endpoint";
    // }

    // @PostMapping("register")
    // public String register(@RequestBody RegistrationRequest request) {
    //     return registrationService.register(request);
    // }

    // @GetMapping(path = "confirm")
    // public String confirm(@RequestParam("token") String token) {
    //     return registrationService.confirmToken(token);
    // }
}
