package com.kwndtwalo.TogetherTransit.domain.auth;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authenticationId;

    //@Column(unique = true, nullable = false)//Enable this when you are done testing.
    private String emailAddress;

    //@Column(unique = true)//Enable this when you are done testing.
    private String password;
    private LocalDateTime lastLogin;
    private boolean locked; //This will be used when the user enter incorrect password more than 3 times.

    protected Authentication() {}

    private Authentication(Builder builder) {
        this.authenticationId = builder.authenticationId;
        this.emailAddress = builder.emailAddress;
        this.password = builder.password;
        this.lastLogin = builder.lastLogin;
        this.locked = builder.locked;
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

    public boolean getLocked() {
        return locked;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "authenticationId=" + getAuthenticationId() +
                ", emailAddress='" + getEmailAddress() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", lastLogin=" + getLastLogin() +
                ", locked=" + getLocked() +
                '}';
    }

    public static class Builder {
        private Long authenticationId;
        private String emailAddress;
        private String password;
        private LocalDateTime lastLogin;
        private boolean locked;

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
        public Builder setLocked(boolean locked) {
            this.locked = locked;
            return this;
        }
        public Builder copy(Authentication authentication) {
            this.authenticationId = authentication.getAuthenticationId();
            this.emailAddress = authentication.getEmailAddress();
            this.password = authentication.getPassword();
            this.lastLogin = authentication.getLastLogin();
            this.locked = authentication.getLocked();
            return this;
        }

        public Authentication build() {return new Authentication(this);}
    }
}
