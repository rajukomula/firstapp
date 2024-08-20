package com.example.backendapp.registration.token;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String ConfirmationToken;

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

    public ConfirmationToken(String ConfirmationToken,
                             Date createdAt,
                             LocalDateTime expiresAt,
                             AppUser appUser) {
        this.ConfirmationToken = ConfirmationToken;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.appUser = appUser;
    }

    public ConfirmationToken(AppUser appUser) {
        this.appUser = appUser;
    }
}