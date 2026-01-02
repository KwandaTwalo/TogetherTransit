package com.kwndtwalo.TogetherTransit.factory.users;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DriverFactoryTest {

    private Authentication authentication = Mockito.mock(Authentication.class);
    private Role role = Mockito.mock(Role.class);
    private Address address = Mockito.mock(Address.class);
    private Contact contact = Mockito.mock(Contact.class);

    private Driver driver1 = DriverFactory.createDriver("12345678910", 1,"Nokuzola", "Ralane",
            LocalDate.now(), User.AccountStatus.ACTIVE, contact, address, authentication, role);

    @Test
    void createDriver() {
        assertNotNull(driver1);
        System.out.println("Driver created successfully: " + driver1);
    }

    @Test
    void testInValidLicenseNumber() {
        Driver driver2 = DriverFactory.createDriver("A23087567", 1,"Nokuzola", "Ralane",
                LocalDate.now(), User.AccountStatus.ACTIVE, contact, address, authentication, role);
        assertNull(driver2);
        System.out.println("license number invalid: " + driver2);
    }
}