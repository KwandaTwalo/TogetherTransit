package com.kwndtwalo.TogetherTransit.dto.auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) for Authentication.
 * Used to transfer data between client and server with validation.
 */
public class AuthenticationDTO {

    private Long authenticationId; // Unique ID for authentication record

    @NotBlank(message = "Email address is required") // Must not be blank
    @Email(message = "Email address must be valid")  // Must follow email format
    private String emailAddress;

    @NotBlank(message = "Password is required") // Must not be blank
    @Size(min = 8, message = "Password must be at least 8 characters long") // Minimum length
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$",
        message = "Password must contain uppercase, lowercase, digit, and special character"
    )
    private String password;

    private LocalDateTime lastLogin; // Tracks last login time

    private boolean locked; // Indicates if account is locked

    // Default constructor required for JSON deserialization
    public AuthenticationDTO() {}    

    // Getters
    public Long getAuthenticationId() { return authenticationId; }
    public String getEmailAddress() { return emailAddress; }
    public String getPassword() { return password; }
    public LocalDateTime getLastLogin() { return lastLogin; }
    public boolean isLocked() { return locked; }
}
