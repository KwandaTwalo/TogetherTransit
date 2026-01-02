package com.kwndtwalo.TogetherTransit.factory.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressFactoryTest {

    private Address address1 = AddressFactory.createAddress("38", "Planter Green",
            "Summer Greens", "Cape Town", 7441);

    @Test
    void createAddress() {
        assertNotNull(address1);
        System.out.println("The address was created successfully: " + address1);
    }

    @Test
    void testInvalidPostalCode() {
        Address address2 = AddressFactory.createAddress("38", "Planter Green",
                "Summer Greens", "Cape Town", 74411);
        assertNull(address2);
        System.out.println("The postal code is invalid: " + address2);
    }

    @Test
    void testEmptyFields() {
        Address address3 = AddressFactory.createAddress("", "Planter Green",
                "Summer Greens", "Cape Town", 7441);
        assertNull(address3);
        System.out.println("There's empty field(s) in the address: " + address3);
    }
}