package com.example.backendapp.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import com.example.backendapp.appuser.AppUser;
import com.example.backendapp.appuser.AppUserRepository;
import com.example.backendapp.email.EmailService;
import com.example.backendapp.registration.token.ConfirmationToken;
import com.example.backendapp.registration.token.ConfirmationTokenRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.ModelAndView;

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

    @Autowired
    private EmailService emailService;

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView, AppUser appUser)
    {
        modelAndView.addObject("appUser", appUser);
        modelAndView.setViewName("register");
        return modelAndView;
    }
    
    
    
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ModelAndView registerUser(ModelAndView modelAndView, AppUser appUser)
    {

    	AppUser existingUser = appUserRepository.findByEmailIdIgnoreCase(appUser.getEmail())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(existingUser != null)
        {
            modelAndView.addObject("message","This email already exists!");
            modelAndView.setViewName("error");
        }
        else
        {
            appUserRepository.save(appUser);

            ConfirmationToken confirmationToken = new ConfirmationToken(appUser);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(appUser.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("YOUR EMAIL ADDRESS");
            mailMessage.setText("To confirm your account, please click here : "
            +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailService.sendEmail(mailMessage);

            modelAndView.addObject("email", appUser.getEmail());

            modelAndView.setViewName("successfulRegisteration");
        }

        return modelAndView;
    }
    

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken)
                                            .orElseThrow(() -> new UsernameNotFoundException("ConfirmationToken not found"));

        if(token != null)
        {
        	AppUser user = appUserRepository.findByEmailIdIgnoreCase(token.getAppUser().getEmail())
                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            user.setEnabled(true);
            appUserRepository.save(user);
            modelAndView.setViewName("accountVerified");
        }
        else
        {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }


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
