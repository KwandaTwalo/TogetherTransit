package com.kwndtwalo.TogetherTransit.dto.users;

import com.kwndtwalo.TogetherTransit.domain.users.Admin;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.dto.generic.AddressDTO;
import com.kwndtwalo.TogetherTransit.dto.generic.ContactDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
//import java.time.LocalDateTime;

/**
 * AdminProfileDTO
 * Purpose: Used for profile updates (firstName, lastName, contact, address).
 * Important: Does NOT expose permissionType or role.
 * This prevents normal admins from escalating privileges.
 */
public class AdminDTO {

    // ===============================
    // USER FIELDS
    // ===============================
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Created date is required")
    private LocalDate createdAt;

    // @NotNull(message = "Account status is required")
    private User.AccountStatus accountStatus;

    // ===============================
    // ADMIN FIELDS
    // ===============================
    // @NotBlank(message = "time and date of last login is required")
    // private LocalDateTime lastLogin; // This is typically managed by the system,
    // not set by the client

    // ===============================
    // ADDRESS FIELDS
    // ===============================
    private AddressDTO address;

    // ===============================
    // CONTACT FIELDS
    // ===============================
    private ContactDTO contact;

    // ===============================
    // CONSTRUCTORS
    // ===============================
    public AdminDTO() {
    }

    // ENTITY → DTO (VERY IMPORTANT FOR RESPONSES)
    public AdminDTO(String firstName, String lastName, LocalDate createdAt,
            User.AccountStatus accountStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.accountStatus = accountStatus;
        // this.lastLogin = lastLogin;
    }

    // ENTITY → DTO mapping (important for responses)
    public AdminDTO(Admin admin) {
        this.firstName = admin.getFirstName();
        this.lastName = admin.getLastName();
        this.createdAt = admin.getCreatedAt();
        this.accountStatus = admin.getAccountStatus();

        
    }


    // ===============================
    // GETTERS ONLY
    // ===============================
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public User.AccountStatus getAccountStatus() {
        return accountStatus;
    }

    // public LocalDateTime getLastLogin() {
    // return lastLogin;
    // }

    public AddressDTO getAddress() {
        return address;
    }

    public ContactDTO getContact() {
        return contact;
    }
}