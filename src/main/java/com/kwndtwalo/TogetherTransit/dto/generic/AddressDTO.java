package com.kwndtwalo.TogetherTransit.dto.generic;

/**DTO stand for: Data Transfer Objects
* It is a simple object used to carry data between layers of an application most commonly:
* From the client (frontend --> backend)
* From backend ---> client*/

//So I will be adding this DTO class in the Address controller so that I can be able to validate the data entered by the user.
//It works hand in hand with Factory class

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;


/**
 * DTO (Data Transfer Object) for Address.
 * Used to transfer data between client and server with validation.
 */
public class AddressDTO {

    @NotBlank(message = "Street number is required")
    @Pattern(regexp = "^[0-9A-Za-z]+$", message = "Street number must be alphanumeric")
    private String streetNumber;

    @NotBlank(message = "Street name is required")
    private String streetName;

    @NotBlank(message = "Suburb is required")
    private String suburb;

    @NotBlank(message = "City is required")
    private String city;

    @Min(value = 0, message = "Postal code must be positive")
    @Max(value = 9999, message = "Postal code must be a valid 4-digit code")
    private int postalCode;

    // Default constructor required for JSON deserialization
    public AddressDTO() {}   
    
    // Getters (needed for JSON serialization)
    public String getStreetNumber() { return streetNumber; }
    public String getStreetName() { return streetName; }
    public String getSuburb() { return suburb; }
    public String getCity() { return city; }
    public int getPostalCode() { return postalCode; }
}

