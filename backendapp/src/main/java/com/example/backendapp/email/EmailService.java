package com.example.backendapp.email;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender javaMailSender;

    // @Autowired
    // public EmailService(JavaMailSender javaMailSender) {
    //     this.javaMailSender = javaMailSender;
    // }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    // private final static Logger LOGGER = LoggerFactory
    //         .getLogger(EmailService.class);

    // private final JavaMailSender mailSender;

    // @Override
    // @Async
    // public void send(String to, String email) {
    //     try {
    //         MimeMessage mimeMessage = mailSender.createMimeMessage();
    //         MimeMessageHelper helper =
    //                 new MimeMessageHelper(mimeMessage, "utf-8");
    //         helper.setText(email, true);
    //         helper.setTo(to);
    //         helper.setSubject("Confirm your email");
    //         helper.setFrom("hello@amigoscode.com"); 
    //         mailSender.send(mimeMessage);
    //     } catch (MessagingException e) {
    //         LOGGER.error("failed to send email", e);
    //         throw new IllegalStateException("failed to send email");
    //     }
    // }
}