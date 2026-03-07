package com.kwndtwalo.TogetherTransit.service.vehicle;


//CONTINUE WITH LOOKING HOW YOU WILL IMPLEMENT THE CUSTOM QUERIES FROM IVEHICLEREPOSITORY. CHECK FROM CHAT INITIALLY SAID YOU MUST SEPARATE THEM AND CREATE EACH SERVICE CLASS.
import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.domain.vehicle.Vehicle;
import com.kwndtwalo.TogetherTransit.factory.auth.AuthenticationFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.AddressFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;
import com.kwndtwalo.TogetherTransit.factory.users.DriverFactory;
import com.kwndtwalo.TogetherTransit.factory.vehicle.VehicleFactory;
import com.kwndtwalo.TogetherTransit.service.users.DriverService;
import com.kwndtwalo.TogetherTransit.service.users.RoleService;
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
class VehicleServiceTest {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    DriverService driverService;

    private static Vehicle savedVehicle;
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

        //Saving the driver first.
        savedDriver = driverService.create(driver);
        assertNotNull(savedDriver);
        assertNotNull(savedDriver.getUserId());


        Vehicle vehicle = VehicleFactory.createVehicle(
                "CA 123-456",
                "School Express",
                Vehicle.VehicleType.MINIBUS,
                15,
                "Toyota",
                "Quantum",
                2020,
                true,
                LocalDate.now().plusYears(1),
                "123456789",
                LocalDate.now().plusYears(1),
                "OUT-Surance",
                LocalDate.now().plusYears(1),
                savedDriver
        );


        assertNotNull(vehicle);

        savedVehicle = vehicleService.create(vehicle);
        assertNotNull(savedVehicle);
        assertNotNull(savedVehicle.getVehicleId());

        System.out.println("Vehicle created successfully: " + savedVehicle);
    }

    @Test
    void b_read() {

        Vehicle found = vehicleService.read(savedVehicle.getVehicleId());

        assertNotNull(found);
        assertEquals(savedVehicle.getVehicleId(), found.getVehicleId());

        System.out.println("Vehicle read successfully: " + found);
    }

    @Test
    void c_update() {

        Vehicle updated = new Vehicle.Builder()
                .copy(savedVehicle)
                .setVehicleName("Updated School Transport")
                .setCapacity(18)
                .build();

        Vehicle result = vehicleService.update(updated);

        assertNotNull(result);
        assertEquals(18, result.getCapacity());

        savedVehicle = result;

        System.out.println("Vehicle updated successfully: " + result);
    }

    @Test
    void d_getAllVehicles() {

        List<Vehicle> vehicles = vehicleService.getAllVehicles();

        assertNotNull(vehicles);
        assertFalse(vehicles.isEmpty());

        System.out.println("All vehicles in system:");
        vehicles.forEach(System.out::println);
    }

    @Test
    void e_delete() {

        boolean deleted = vehicleService.delete(savedVehicle.getVehicleId());

        assertTrue(deleted);
        System.out.println("Vehicle deleted successfully: " + savedVehicle.getVehicleId());
    }
}