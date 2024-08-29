package com.example.backendapp.registration.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import com.example.backendapp.appuser.AppUser;

import java.util.Date;
import java.util.UUID;

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

    @Column(nullable = false, unique = true)
    private String confirmationToken;

    @Column(nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    public ConfirmationToken(String confirmationToken, Date createdAt, AppUser appUser) {
        this.confirmationToken = confirmationToken;
        this.createdAt = createdAt;
        this.appUser = appUser;
    }

    public ConfirmationToken(AppUser appUser) {
        this.confirmationToken = UUID.randomUUID().toString(); // Generate a unique token
        this.createdAt = new Date(); // Set creation time to now
        this.appUser = appUser;
    }
}
