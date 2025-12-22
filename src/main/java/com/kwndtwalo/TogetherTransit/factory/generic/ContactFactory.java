package com.kwndtwalo.TogetherTransit.factory.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.util.Helper;

public class ContactFactory {

    public static Contact createContact(String phoneNumber, String emergencyNumber) {

        if (!Helper.isValidPhoneNumber(phoneNumber) || !Helper.isValidPhoneNumber(emergencyNumber)) {return null;}
        return new Contact.Builder()
                .setPhoneNumber(phoneNumber)
                .setEmergencyNumber(emergencyNumber)
                .build();
    }
}
