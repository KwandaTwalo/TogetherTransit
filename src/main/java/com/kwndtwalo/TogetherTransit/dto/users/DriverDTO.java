package com.kwndtwalo.TogetherTransit.dto.users;

import com.kwndtwalo.TogetherTransit.domain.users.Driver;

public class DriverDTO extends UserDTO {

    private String licenseNumber;
    private int ratingAverage;

    public DriverDTO(Driver driver) {
        super(driver);
        this.licenseNumber = driver.getLicenseNumber();
        this.ratingAverage = driver.getRatingAverage();
    }

    @Override
    public String toString() {
        return "\nDriverDTO{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                ", accountStatus='" + accountStatus + '\'' +
                ", role='" + role + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", ratingAverage=" + ratingAverage +
                '}';
    }
}