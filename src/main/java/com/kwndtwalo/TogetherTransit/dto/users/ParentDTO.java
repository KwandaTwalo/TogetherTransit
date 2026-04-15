package com.kwndtwalo.TogetherTransit.dto.users;

import java.time.LocalDate;

import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.dto.generic.AddressDTO;
import com.kwndtwalo.TogetherTransit.dto.generic.ContactDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ParentDTO {
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
    public ParentDTO() {
    }

    // ENTITY → DTO (VERY IMPORTANT FOR RESPONSES)
    public ParentDTO(Parent parent) {
        this.firstName = parent.getFirstName();
        this.lastName = parent.getLastName();
        this.createdAt = parent.getCreatedAt();
        //this.accountStatus = parent.getAccountStatus();
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

    public AddressDTO getAddress() {
        return address;
    }

    public ContactDTO getContact() {
        return contact;
    }

}