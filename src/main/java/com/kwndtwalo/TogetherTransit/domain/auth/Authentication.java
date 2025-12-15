package com.kwndtwalo.TogetherTransit.domain.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authenticationId;
    private String emailAddress;
    private String password;
    private LocalDateTime lastLogin;
    private boolean is_locked; //This will be used when the user enter incorrect password more than 3 times.

    protected Authentication() {}

    private Authentication(Builder builder) {
        this.authenticationId = builder.authenticationId;
        this.emailAddress = builder.emailAddress;
        this.password = builder.password;
        this.lastLogin = builder.lastLogin;
        this.is_locked = builder.is_locked;
    }


    public Long getAuthenticationId() {
        return authenticationId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public boolean getIs_locked() {
        return is_locked;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "authenticationId=" + getAuthenticationId() +
                ", emailAddress='" + getEmailAddress() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", lastLogin=" + getLastLogin() +
                ", is_locked=" + getIs_locked() +
                '}';
    }

    public static class Builder {
        private Long authenticationId;
        private String emailAddress;
        private String password;
        private LocalDateTime lastLogin;
        private boolean is_locked;

        public Builder setAuthenticationId(Long authenticationId) {
            this.authenticationId = authenticationId;
            return this;
        }
        public Builder setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }
        public Builder setLastLogin(LocalDateTime lastLogin) {
            this.lastLogin = lastLogin;
            return this;
        }
        public Builder setIs_locked(boolean is_locked) {
            this.is_locked = is_locked;
            return this;
        }
        public Builder copy(Authentication authentication) {
            this.authenticationId = authentication.getAuthenticationId();
            this.emailAddress = authentication.getEmailAddress();
            this.password = authentication.getPassword();
            this.lastLogin = authentication.getLastLogin();
            this.is_locked = authentication.getIs_locked();
            return this;
        }

        public Authentication build() {return new Authentication(this);}
    }
}
