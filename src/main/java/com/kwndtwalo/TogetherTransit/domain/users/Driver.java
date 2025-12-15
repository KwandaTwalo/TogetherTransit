package com.kwndtwalo.TogetherTransit.domain.users;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Driver extends User {

    private Long licenseNumber;
    private int ratingAverage;

    protected Driver() {
    }
    private Driver(Builder builder) {
        this.licenseNumber = builder.licenseNumber;
        this.ratingAverage = builder.ratingAverage;
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

    public Long getLicenseNumber() {
        return licenseNumber;
    }

    public int getRatingAverage() {
        return ratingAverage;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "licenseNumber=" + getLicenseNumber() +
                ", ratingAverage=" + getRatingAverage() +
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
        private Long licenseNumber;
        private int ratingAverage;
        private Long userId;
        private String firstName;
        private String lastName;
        private LocalDate createdAt;
        private AccountStatus accountStatus;
        private Contact contact;
        private Address address;
        private Authentication authentication;
        private Role role;

        public Builder setLicenseNumber(Long licenseNumber) {
            this.licenseNumber = licenseNumber;
            return this;
        }
        public Builder setRatingAverage(int ratingAverage) {
            this.ratingAverage = ratingAverage;
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

        public Builder copy(Driver driver) {
            this.licenseNumber = driver.getLicenseNumber();
            this.ratingAverage = driver.getRatingAverage();
            this.userId = driver.getUserId();
            this.firstName = driver.getFirstName();
            this.lastName = driver.getLastName();
            this.createdAt = driver.getCreatedAt();
            this.accountStatus = driver.accountStatus;
            this.contact = driver.getContact();
            this.address = driver.getAddress();
            this.authentication = driver.getAuthentication();
            this.role = driver.getRole();
            return this;
        }
        public Driver build() {return new Driver(this);}

    }
}
