package com.kwndtwalo.TogetherTransit.factory.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.util.Helper;

public class AddressFactory {

    public static Address createAddress(String streetNumber,
                                        String streetName,
                                        String suburb,
                                        String city,
                                        int postalCode) {

        if (!Helper.isValidString(streetNumber) ||
        !Helper.isValidString(streetName) ||
        !Helper.isValidString(suburb) ||
        !Helper.isValidString(city) ||
        !Helper.isValidPostalCode(postalCode)) { return null; }

        return new Address.Builder()
                .setStreetNumber(streetNumber)
                .setStreetName(streetName)
                .setSuburb(suburb)
                .setCity(city)
                .setPostalCode(postalCode)
                .build();
    }

}
