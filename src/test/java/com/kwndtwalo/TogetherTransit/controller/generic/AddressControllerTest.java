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

import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.factory.generic.AddressFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AddressControllerTest {

    private static Address address = AddressFactory.createAddress(
            "45",
            "Voortrekker Street",
            "Bellville",
            "Cape Town",
            7530);
    private static Address addressWithID;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/togetherTransit/address";

    @Test
    void a_testCreate() {
        String url = BASE_URL + "/create";

        ResponseEntity<Address> response = restTemplate.postForEntity(url, address, Address.class);
        assertNotNull(response.getBody());
        System.out.println(response.getBody());

        addressWithID = response.getBody();
        assertNotNull(addressWithID);
        assertEquals(address.getStreetNumber(), addressWithID.getStreetNumber());
        assertEquals(address.getStreetName(), addressWithID.getStreetName());
        assertEquals(address.getSuburb(), addressWithID.getSuburb());
        assertEquals(address.getCity(), addressWithID.getCity());
        assertEquals(address.getPostalCode(), addressWithID.getPostalCode());
        System.out.println("Created Address: " + addressWithID);
    }

    @Test
    void b_testRead() {
        String url = BASE_URL + "/read/" + addressWithID.getAddressId();
        ResponseEntity<Address> response = restTemplate.getForEntity(url, Address.class);
        assertNotNull(response.getBody());
        System.out.println("Read Address: " + response.getBody());
    }

    @Test
    void c_testUpdate() {
        assertNotNull(addressWithID, "Address with ID should not be null before update test");
        String url = BASE_URL + "/update/" + addressWithID.getAddressId();

        Address addressToUpdate = new Address.Builder()
                .copy(addressWithID)
                .setStreetNumber("123")
                .setStreetName("Updated Street")
                .setSuburb("Updated Suburb")
                .setCity("Updated City")
                .setPostalCode(9999)
                .build();
        this.restTemplate.put(url, addressToUpdate);

        // Verify update
        ResponseEntity<Address> readResponse = this.restTemplate
                .getForEntity(BASE_URL + "/read/" + addressToUpdate.getAddressId(), Address.class);
        assertEquals(HttpStatus.OK, readResponse.getStatusCode());

        Address newAddress = readResponse.getBody();
        assertEquals("123", newAddress.getStreetNumber());
        assertEquals("Updated Street", newAddress.getStreetName());
        assertEquals("Updated Suburb", newAddress.getSuburb());
        assertEquals("Updated City", newAddress.getCity());
        assertEquals(9999, newAddress.getPostalCode());
        System.out.println("Updated Address: " + newAddress);
    }

    @Test
    void d_testGetAll() {
        String url = BASE_URL + "/getAllAddresses";
        ResponseEntity<Address[]> response = restTemplate.getForEntity(url, Address[].class);
        assertNotNull(response.getBody());
        System.out.println("All Addresses: ");
        for (Address address : response.getBody()) {
            System.out.println(address);
        }
    }

    @Test
    void e_testGetByCity() {

    }

    @Test
    void f_testGetByPostalCode() {

    }

    @Test
    void g_testGetBySuburb() {

    }

    @Test
    void h_testSearchStreet() {

    }

    @Test
    void i_testDelete() {
        String url = BASE_URL + "/delete/" + addressWithID.getAddressId();
        System.out.println("Deleting Address with ID: " + addressWithID.getAddressId());
        restTemplate.delete(url);

        // verify deletion
        ResponseEntity<Address> readAddress = restTemplate
                .getForEntity(BASE_URL + "/read/" + addressWithID.getAddressId(), Address.class);

        // Expecting 404 Not Found after deletion
        assertEquals(HttpStatus.NOT_FOUND, readAddress.getStatusCode());
        System.out.println("After deletion, read response status: " + readAddress.getStatusCode());
    }

}
