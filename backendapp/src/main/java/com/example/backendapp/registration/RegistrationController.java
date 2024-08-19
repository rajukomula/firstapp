package com.example.backendapp.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.example.backendapp.appuser.AppUserRepository;
import org.springframework.web.servlet.ModelAndView;



@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class RegistrationController {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView, UserEntity userEntity)
    {
        modelAndView.addObject("userEntity", userEntity);
        modelAndView.setViewName("register");
        return modelAndView;
    }
    
    
    
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ModelAndView registerUser(ModelAndView modelAndView, UserEntity userEntity)
    {

    	UserEntity existingUser = userRepository.findByEmailIdIgnoreCase(userEntity.getEmailId());
        if(existingUser != null)
        {
            modelAndView.addObject("message","This email already exists!");
            modelAndView.setViewName("error");
        }
        else
        {
            userRepository.save(userEntity);

            ConfirmationToken confirmationToken = new ConfirmationToken(userEntity);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userEntity.getEmailId());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("YOUR EMAIL ADDRESS");
            mailMessage.setText("To confirm your account, please click here : "
            +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailService.sendEmail(mailMessage);

            modelAndView.addObject("emailId", userEntity.getEmailId());

            modelAndView.setViewName("successfulRegisteration");
        }

        return modelAndView;
    }
    

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
        	UserEntity user = userRepository.findByEmailIdIgnoreCase(token.getUserEntity().getEmailId());
            user.setEnabled(true);
            userRepository.save(user);
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
