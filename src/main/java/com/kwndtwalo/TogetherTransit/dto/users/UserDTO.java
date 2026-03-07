package com.kwndtwalo.TogetherTransit.dto.users;

import com.kwndtwalo.TogetherTransit.domain.users.User;

import java.time.LocalDate;

public abstract class UserDTO {

    protected Long userId;
    protected String firstName;
    protected String lastName;
    protected LocalDate createdAt;
    protected String accountStatus;
    protected String role;

    protected UserDTO(User user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.createdAt = user.getCreatedAt();
        this.accountStatus = user.getAccountStatus().name();
        this.role = user.getRole() != null
                ? user.getRole().getRoleName().name()
                : null;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                ", accountStatus='" + accountStatus + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}