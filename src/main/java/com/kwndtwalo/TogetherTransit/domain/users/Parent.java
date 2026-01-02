package com.kwndtwalo.TogetherTransit.domain.users;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Parent extends User {

    protected Parent() {}

    private Parent(Builder builder) {
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

    @Override
    public String toString() {
        return "Parent{" +
                "parentId=" + getUserId() +
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
        private Long userId;
        private String firstName;
        private String lastName;
        private LocalDate createdAt;
        private AccountStatus accountStatus;
        private Contact contact;
        private Address address;
        private Authentication authentication;
        private Role role;

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

        public Builder copy(Parent parent) {
            this.userId = parent.getUserId();
            this.firstName = parent.getFirstName();
            this.lastName = parent.getLastName();
            this.createdAt = parent.getCreatedAt();
            this.accountStatus = parent.accountStatus;
            this.contact = parent.getContact();
            this.address = parent.getAddress();
            this.authentication = parent.getAuthentication();
            this.role = parent.getRole();
            return this;
        }

        public Parent build() {return new Parent(this);}
    }
}
