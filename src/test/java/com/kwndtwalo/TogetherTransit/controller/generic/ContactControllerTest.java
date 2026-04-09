package com.kwndtwalo.TogetherTransit.controller.generic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ContactControllerTest {

    private static Contact contact = ContactFactory.createContact(
            "0732345678",
            "0768765432");

    private static Contact contact_withID;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/togetherTransit/contact";

    @Test
    void a_testCreate() {
        String url = BASE_URL + "/create";
        ResponseEntity<Contact> response = restTemplate.postForEntity(url, contact, Contact.class);
        assertNotNull(response.getBody());
        System.out.println(response.getBody());
        contact_withID = response.getBody();
        assertNotNull(contact_withID);
        assertEquals(contact.getPhoneNumber(), contact_withID.getPhoneNumber());
        assertEquals(contact.getEmergencyNumber(), contact_withID.getEmergencyNumber());
        System.out.println("Created Contact: " + contact_withID);
    }

    @Test
    void b_testRead() {
        String url = BASE_URL + "/read/" + contact_withID.getContactId();
        ResponseEntity<Contact> response = restTemplate.getForEntity(url, Contact.class);
        assertNotNull(response.getBody());
        System.out.println("Read Contact: " + response.getBody());
    }

    @Test
    void c_testUpdate() {

        assertNotNull(contact_withID, "Contact with ID should not be null before update test");
        String url = BASE_URL + "/update/" + contact_withID.getContactId();

        Contact updateContact = new Contact.Builder()
                .copy(contact_withID)
                .setPhoneNumber("0798765432")
                .setEmergencyNumber("0712345678")
                .build();
        this.restTemplate.put(url, updateContact);

        // Verify update
        ResponseEntity<Contact> readResponse = this.restTemplate
                .getForEntity(BASE_URL + "/read/" + updateContact.getContactId(), Contact.class);
        assertEquals(HttpStatus.OK, readResponse.getStatusCode());

        Contact newContact = readResponse.getBody();
        assertEquals("0798765432", newContact.getPhoneNumber());
        assertEquals("0712345678", newContact.getEmergencyNumber());
        System.out.println("Updated Contact: " + newContact);
    }

    @Test
    void d_testGetAll() {
        String url = BASE_URL + "/getAllContacts";
        ResponseEntity<Contact[]> response = restTemplate.getForEntity(url, Contact[].class);
        assertNotNull(response.getBody());
        System.out.println("All Contacts: ");
        for (Contact c : response.getBody()) {
            System.out.println(c);
        }
    }

    @Test
    void e_testGetByPhone() {

    }

    @Test
    void f_testGetByPhoneAndEmergency() {

    }

    @Test
    void g_testSearchByPartialPhone() {

    }

    @Test
    void h_testDelete() {
        String url = BASE_URL + "/delete/" + contact_withID.getContactId();
        System.out.println("Deleting Contact with ID: " + contact_withID.getContactId());
        this.restTemplate.delete(url);

        // Verify deletion
        ResponseEntity<Contact> readContact = this.restTemplate
                .getForEntity(BASE_URL + "/read/" + contact_withID.getContactId(), Contact.class);

        // Expecting 404 Not Found after deletion
        assertEquals(HttpStatus.NOT_FOUND, readContact.getStatusCode());
        System.out.println("After deletion, read response status: " + readContact.getStatusCode());

    }

}
