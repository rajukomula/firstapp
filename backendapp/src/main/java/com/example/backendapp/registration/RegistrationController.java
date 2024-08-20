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

    // @RequestMapping(value="/register", method = RequestMethod.GET)
    // public ModelAndView displayRegistration(ModelAndView modelAndView)
    // {
    //     System.out.println("get one triggered");

    //     modelAndView.addObject("appUser", new AppUser());
    //     modelAndView.setViewName("register");
    //     return modelAndView;
    // }
    @PostMapping("/print")
    public void printStrings(@RequestBody AppUser appUser) {
        // Print the received strings to the console
        System.out.println("First Name: " + appUser.getFirstName());
        System.out.println("Last Name: " + appUser.getLastName());
    }
    
    @PostMapping("/register")
    public void registerUser(@RequestBody AppUser appUser)
    {
        System.out.println("post one triggered");



    	AppUser existingUser = appUserRepository.findByEmailIgnoreCase(appUser.getEmail())
                                .orElse(null);

        if(existingUser != null)
        {
            // modelAndView.addObject("message","This email already exists!");
            // modelAndView.setViewName("error");

            System.out.println("if block triggered");
        }
        else
        {
            System.out.println("else block triggered");

            appUserRepository.save(appUser);

            ConfirmationToken confirmationToken = new ConfirmationToken(appUser);
            System.out.println("before");

            confirmationTokenRepository.save(confirmationToken);
            System.out.println("after");



            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(appUser.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("testemailforapp9@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
            +"https://musical-train-7vrjpgwx64xj3rpv5-8080.app.github.dev/confirm-account?token="+confirmationToken.getConfirmationToken());

            System.out.println("email debugggg");

            System.out.println("Sending email to: " + appUser.getEmail());
            System.out.println("Email subject: " + mailMessage.getSubject());
            System.out.println("Email from: " + mailMessage.getFrom());
            System.out.println("Email content: " + mailMessage.getText());
            
            emailService.sendEmail(mailMessage);

            // modelAndView.addObject("email", appUser.getEmail());

            System.out.println("two triggered");

            // modelAndView.setViewName("successfulRegisteration");
        }

        // return modelAndView;
    }
    

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken)
                                            .orElseThrow(() -> new UsernameNotFoundException("ConfirmationToken not found"));

        if(token != null)
        {
        	AppUser user = appUserRepository.findByEmailIgnoreCase(token.getAppUser().getEmail())
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
