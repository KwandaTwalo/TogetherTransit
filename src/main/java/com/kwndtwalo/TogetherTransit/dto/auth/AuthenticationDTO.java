package com.kwndtwalo.TogetherTransit.dto.auth;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;

import java.time.LocalDateTime;

public class AuthenticationDTO {

    private Long authenticationId;
    private String emailAddress;
    private LocalDateTime lastLogin;
    private boolean locked;

    public AuthenticationDTO(Authentication auth) {
        this.authenticationId = auth.getAuthenticationId();
        this.emailAddress = auth.getEmailAddress();
        this.lastLogin = auth.getLastLogin();
        this.locked = auth.getLocked();
    }

    @Override
    public String toString() {
        return "\nAuthenticationDTO{" +
                "authenticationId=" + authenticationId +
                ", emailAddress='" + emailAddress + '\'' +
                ", lastLogin=" + lastLogin +
                ", locked=" + locked +
                '}';
    }
}