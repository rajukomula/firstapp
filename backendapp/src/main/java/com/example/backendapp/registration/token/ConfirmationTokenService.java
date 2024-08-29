package com.example.backendapp.registration.token;

import com.example.backendapp.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    // Correct method to find a token by AppUser
    public Optional<ConfirmationToken> getTokenByUser(AppUser appUser) {
        return confirmationTokenRepository.findByAppUser(appUser);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByConfirmationToken(token);
    }
}





    // public int setConfirmedAt(String token) {
    //     return confirmationTokenRepository.setConfirmedAt(
    //             token, LocalDateTime.now());
    // }