package com.kwndtwalo.TogetherTransit.dto.generic;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ContactDTO {

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(\\+?27|0)\\d{9}$", message = "Phone number must be a valid South African number")
    private String phoneNumber;

    @NotBlank(message = "Emergency number is required")
    @Pattern(regexp = "^(\\+?27|0)\\d{9}$", message = "Emergency number must be a valid South African number")
    private String emergencyNumber;

    // Default constructor required for JSON deserialization
    public ContactDTO() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }
}
