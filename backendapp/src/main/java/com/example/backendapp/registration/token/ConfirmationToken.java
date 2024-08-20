package com.example.backendapp.registration.token;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;


import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;


import com.example.backendapp.appuser.AppUser;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String confirmationToken;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

//     private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    public ConfirmationToken(String confirmationToken,
                             Date createdAt,
                             LocalDateTime expiresAt,
                             AppUser appUser) {
        this.confirmationToken = confirmationToken;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.appUser = appUser;
    }

    public ConfirmationToken(AppUser appUser) {
        this.confirmationToken = UUID.randomUUID().toString(); // Generate a unique token
        this.createdAt = new Date();
        this.expiresAt = LocalDateTime.now().plusMinutes(15);  // Set expiration (e.g., 15 minutes)
        this.appUser = appUser;
    }
}