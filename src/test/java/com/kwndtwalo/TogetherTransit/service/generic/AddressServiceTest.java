package com.kwndtwalo.TogetherTransit.service.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.factory.generic.AddressFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    //will be used to store the created address that has an ID.
    private static Address addressWithID;

    @Test
    void a_create() {

        addressWithID = AddressFactory.createAddress(
                "12",
                "Main Road",
                "Claremont",
                "Cape Town",
                7708
        );
        assertNotNull(addressWithID);
        Address createdAddress = addressService.create(addressWithID);
        assertNotNull(createdAddress);
        assertNotNull(createdAddress.getAddressId());
        System.out.println("Address created: " + addressWithID);
    }

    @Test
    void b_read() {
        //Fetching the address using its ID.
        Address readAddress = addressService.read(addressWithID.getAddressId());
        assertNotNull(readAddress);
        assertEquals(addressWithID.getCity(), readAddress.getCity());
        System.out.println("Read address: " +readAddress);
    }

    @Test
    void c_update() {

        Address updatedAddress = new Address.Builder()
                .copy(addressWithID)
                .setStreetName("updated Road")
                .build();

        Address savedAddress = addressService.update(updatedAddress);
        assertNotNull(savedAddress);
        assertEquals(updatedAddress.getStreetName(), savedAddress.getStreetName());
        System.out.println("Updated address: " +savedAddress);
    }

    @Test
    void e_delete() {
        // Delete should return true if the entity existed.
        boolean deleted = addressService.delete(addressWithID.getAddressId());
        assertTrue(deleted);
        System.out.println("Deleted address: " +deleted);

        //verify the address no longer exists.
        Address readAddress = addressService.read(addressWithID.getAddressId());
        assertNull(readAddress);
        System.out.println("address should be null: " + readAddress);
    }

    @Test
    void d_getAllAddresses() {
        //Ensure the system can return all addresses.
        List<Address> addresses = addressService.getAllAddresses();

        assertNotNull(addresses);
        assertTrue(addresses.size() > 0);
        System.out.println("addresses: " + addresses);
    }

    @Test
    void f_findDuplicateAddress() {
        //create and save a new address to see if this method can find duplicates.
        Address newAddress = addressService.create(
                AddressFactory.createAddress(
                        "25",
                        "Long Street",
                        "CBD",
                        "Cape Town",
                        8001
                )
        );

        // Attempt to find the same address again.
        Address duplicateAddress = addressService.findDuplicateAddress(newAddress);
        assertNotNull(duplicateAddress);
        assertEquals(newAddress.getAddressId(), duplicateAddress.getAddressId());
        System.out.println("duplicate address: " + duplicateAddress);
    }

    @Test
    void g_getAddressesByCity() {
        //Query addresses by city (case-insensitive) meaning it will find it weather is in lower or upper cases.
        List<Address> results = addressService.getAddressesByCity("cape town");

        //check if it's null or not but it should not be null.
        assertNotNull(results);
        assertFalse(results.isEmpty());
        System.out.println("results: " + results);
    }

    @Test
    void h_getAddressesBySuburb() {
        //Query addresses by suburb.
        List<Address> results = addressService.getAddressesBySuburb("CBD");
        assertNotNull(results);
        System.out.println("results: " + results);
    }

    @Test
    void i_getAddressesByPostalCode() {
        // Query addresses using postal code.
        List<Address> results = addressService.getAddressesByPostalCode(8001);
        assertNotNull(results);
        System.out.println("results: " + results);
    }

    @Test
    void j_searchByStreet() {
        // Search by partial street name.
        List<Address> results = addressService.searchByStreet("25", "long");

        assertNotNull(results);
        assertFalse(results.isEmpty());
        System.out.println("results: " + results);
    }
}