package com.kwndtwalo.TogetherTransit.domain.users;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Admin extends User {

    private LocalDateTime lastLogin;

    @ManyToOne
    @JoinColumn(name = "permissionId")
    private PermissionLevel permissionLevel;

    protected Admin() {
    }

    private Admin(Builder builder) {
        this.lastLogin = builder.lastLogin;
        this.userId = builder.userId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.createdAt = builder.createdAt;
        this.accountStatus = builder.accountStatus;
        this.contact = builder.contact;
        this.address = builder.address;
        this.authentication = builder.authentication;
        this.role = builder.role;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "lastLogin=" + getLastLogin() +
                ", userId=" + getUserId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", accountStatus='" + getAccountStatus() + '\'' +
                ", contact=" + getContact() +
                ", address=" + getAddress() +
                ", authentication=" + getAuthentication() +
                ", role=" + getRole() +
                '}';
    }

    public static class Builder {
        private LocalDateTime lastLogin;
        private Long userId;
        private String firstName;
        private String lastName;
        private LocalDate createdAt;
        private AccountStatus accountStatus;
        private Contact contact;
        private Address address;
        private Authentication authentication;
        private Role role;

        public Builder setLastLogin(LocalDateTime lastLogin) {
            this.lastLogin = lastLogin;
            return this;
        }
        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }
        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder setCreatedAt(LocalDate createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public Builder setAccountStatus(AccountStatus accountStatus) {
            this.accountStatus = accountStatus;
            return this;
        }
        public Builder setContact(Contact contact) {
            this.contact = contact;
            return this;
        }
        public Builder setAddress(Address address) {
            this.address = address;
            return this;
        }
        public Builder setAuthentication(Authentication authentication) {
            this.authentication = authentication;
            return this;
        }
        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder copy(Admin admin) {
            this.lastLogin = admin.getLastLogin();
            this.userId = admin.getUserId();
            this.firstName = admin.getFirstName();
            this.lastName = admin.getLastName();
            this.createdAt = admin.getCreatedAt();
            this.accountStatus = admin.accountStatus;
            this.contact = admin.getContact();
            this.address = admin.getAddress();
            this.authentication = admin.getAuthentication();
            this.role = admin.getRole();
            return this;
        }

        public Admin build() {return new Admin(this);}

    }
}
