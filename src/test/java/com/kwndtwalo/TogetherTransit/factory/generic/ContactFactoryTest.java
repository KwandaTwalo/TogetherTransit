package com.kwndtwalo.TogetherTransit.factory.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactFactoryTest {

    private Contact contact1 = ContactFactory.createContact("+27679844408", "0829739622");

    @Test
    void createContact() {
        assertNotNull(contact1);
        System.out.println("Contact created successfully: " + contact1);
    }

    @Test
    void testIsValidPhoneNumber() {
        Contact contact2 = ContactFactory.createContact("+27679844408", "091 514 5001");
        assertNull(contact2, "Invalid phone number");
        System.out.println("Invalid phone number: " + contact2);
    }
}