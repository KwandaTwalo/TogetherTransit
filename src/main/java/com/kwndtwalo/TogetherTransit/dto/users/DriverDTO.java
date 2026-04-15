package com.kwndtwalo.TogetherTransit.dto.users;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.dto.generic.AddressDTO;
import com.kwndtwalo.TogetherTransit.dto.generic.ContactDTO;

/**
 * DriverDTO
 * Used to RECEIVE data from client (Postman / Mobile App)
 * Includes validation rules aligned with Helper + Factory
 */
public class DriverDTO {

    // ===============================
    // USER FIELDS
    // ===============================
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Created date is required")
    private LocalDate createdAt;

    //@NotNull(message = "Account status is required")
    private User.AccountStatus accountStatus;

    // ===============================
    // DRIVER FIELDS
    // ===============================
    @NotBlank(message = "License number is required")
    @Pattern(regexp = "^[0-9\\s]{8,15}$")
    private String licenseNumber;

    @Min(1)
    @Max(5)
    private int ratingAverage;

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
    public DriverDTO() {}

    // ENTITY → DTO (VERY IMPORTANT FOR RESPONSES)
    public DriverDTO(Driver driver) {
        this.firstName = driver.getFirstName();
        this.lastName = driver.getLastName();
        this.createdAt = driver.getCreatedAt();
        //this.accountStatus = driver.getAccountStatus();
        this.licenseNumber = driver.getLicenseNumber();
        this.ratingAverage = driver.getRatingAverage();
    }

    // ===============================
    // GETTERS ONLY
    // ===============================
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getCreatedAt() { return createdAt; }
    public User.AccountStatus getAccountStatus() { return accountStatus; }
    public String getLicenseNumber() { return licenseNumber; }
    public int getRatingAverage() { return ratingAverage; }
    public AddressDTO getAddress() { return address; }
    public ContactDTO getContact() { return contact; }
}