package com.kwndtwalo.TogetherTransit.dto.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Address;

/**DTO stand for: Data Transfer Objects
* It is a simple object used to carry data between layers of an application most commonly:
* From the client (frontend --> backend)
* From backend ---> client*/

//So I will be adding this DTO class in the Address controller so that I can be able to validate the data entered by the user.
//It works hand in hand with Factory class

public class AddressDTO {

    private String streetNumber;
    private String streetName;
    private String suburb;
    private String city;
    private int postalCode;

    // Default constructor required for JSON deserialization
    public AddressDTO(Address address) {
        this.streetNumber = address.getStreetNumber();
        this.streetName = address.getStreetName();
        this.suburb = address.getSuburb();
        this.city = address.getCity();
        this.postalCode = address.getPostalCode();
    }
}
