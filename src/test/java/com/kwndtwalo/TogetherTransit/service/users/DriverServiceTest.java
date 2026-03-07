package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.factory.auth.AuthenticationFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.AddressFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;
import com.kwndtwalo.TogetherTransit.factory.users.DriverFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class DriverServiceTest {

    @Autowired
    private DriverService driverService;

    @Autowired
    private RoleService roleService;

    private static Driver savedDriver;

    @Test
    void a_create() {

        Contact contact = ContactFactory.createContact(
                "0712345678",
                "0798765432"
        );

        Address address = AddressFactory.createAddress(
                "12",
                "Main Road",
                "Claremont",
                "Cape Town",
                7708
        );

        Authentication authentication = AuthenticationFactory.createAuthentication(
                "sandile@gmail.com",
                "San@1234",
                LocalDateTime.now(),
                false
        );

        Role role = roleService.getByRoleName(Role.RoleName.DRIVER);

        Driver driver = DriverFactory.createDriver(
                "12345678910",
                2,
                "Sandile",
                "Sibiya",
                LocalDate.now(),
                User.AccountStatus.ACTIVE,
                contact,
                address,
                authentication,
                role
        );

        assertNotNull(driver);
        savedDriver = driverService.create(driver);
        assertNotNull(savedDriver);
        assertNotNull(savedDriver.getUserId());
        System.out.println("Driver created successfully: " + savedDriver);
    }

    @Test
    void b_read() {
        Driver found = driverService.read(savedDriver.getUserId());
        assertNotNull(found);
        assertEquals(savedDriver.getUserId(), found.getUserId());
        System.out.println("Driver read successfully: " + found);
    }

    @Test
    void c_update() {
        Driver updated = new Driver.Builder()
                .copy(savedDriver)
                .setLastName("Enough")
                .build();

        Driver result = driverService.update(updated);
        assertNotNull(result);
        assertEquals("Enough", result.getLastName());

        savedDriver = result;
        System.out.println("Driver update successfully: " + result);
    }

    @Test
    void d_getAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        assertNotNull(drivers);
        assertFalse(drivers.isEmpty());
        System.out.println("Drivers in the system: " + drivers);

    }

    @Test
    void e_delete() {
        boolean deleted = driverService.delete(savedDriver.getUserId());
        assertTrue(deleted);
        System.out.println("Driver deleted successfully: " + savedDriver);
    }
}