package com.kwndtwalo.TogetherTransit.service.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    // Used to hold a persisted contact across tests
    private static Contact contactWithID;

    @Test
    void a_create() {
        Contact contact = ContactFactory.createContact(
                "0712345678",
                "0798765432"
        );

        assertNotNull(contact, "Factory should return a valid Contact");

        contactWithID = contactService.create(contact);

        assertNotNull(contactWithID);
        assertNotNull(contactWithID.getContactId());

        System.out.println("CREATED CONTACT: " + contactWithID);
    }

    @Test
    void b_read() {
        assertNotNull(contactWithID, "Contact must exist from create test");

        Contact readContact = contactService.read(contactWithID.getContactId());

        assertNotNull(readContact);
        assertEquals(contactWithID.getContactId(), readContact.getContactId());

        System.out.println("READ CONTACT: " + readContact);
    }

    @Test
    void c_update() {
        assertNotNull(contactWithID);

        Contact updatedContact = new Contact.Builder()
                .copy(contactWithID)
                .setEmergencyNumber("0811111111") // change emergency number
                .build();

        Contact saved = contactService.update(updatedContact);

        assertNotNull(saved);
        assertEquals("0811111111", saved.getEmergencyNumber());

        contactWithID = saved; // update reference

        System.out.println("UPDATED CONTACT: " + saved);
    }

    @Test
    void d_getAllContacts() {
        List<Contact> contacts = contactService.getAllContacts();

        assertNotNull(contacts);
        assertFalse(contacts.isEmpty());

        System.out.println("ALL CONTACTS:");
        contacts.forEach(System.out::println);
    }

    @Test
    void e_getByPhoneNumber() {
        Contact found = contactService
                .getByPhoneNumber(contactWithID.getPhoneNumber());

        assertNotNull(found);
        assertEquals(contactWithID.getContactId(), found.getContactId());

        System.out.println("FOUND BY PHONE NUMBER: " + found);
    }

    @Test
    void f_getByPhoneAndEmergency() {
        Contact found = contactService.getByPhoneAndEmergency(
                contactWithID.getPhoneNumber(),
                contactWithID.getEmergencyNumber()
        );

        assertNotNull(found);
        assertEquals(contactWithID.getContactId(), found.getContactId());

        System.out.println("FOUND BY PHONE + EMERGENCY: " + found);
    }

    @Test
    void g_searchByPartialPhone() {
        List<Contact> results = contactService.searchByPartialPhone("5678");

        assertNotNull(results);
        assertFalse(results.isEmpty());

        System.out.println("PARTIAL PHONE SEARCH RESULTS:");
        results.forEach(System.out::println);
    }

    @Test
    void h_delete() {
        assertNotNull(contactWithID);

        boolean deleted = contactService.delete(contactWithID.getContactId());

        assertTrue(deleted);

        Contact deletedContact = contactService.read(contactWithID.getContactId());
        assertNull(deletedContact);

        System.out.println("CONTACT DELETED SUCCESSFULLY");
    }
}
