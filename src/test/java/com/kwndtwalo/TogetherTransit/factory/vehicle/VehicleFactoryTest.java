package com.kwndtwalo.TogetherTransit.factory.vehicle;

import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.vehicle.Vehicle;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class VehicleFactoryTest {


    private Driver mockDriver = mock(Driver.class);

    /* ============================
       SUCCESS CASE
       ============================ */

    @Test
    void createVehicle_success() {

        Vehicle vehicle = VehicleFactory.createVehicle(
                "CA123456",                       // valid SA plate
                "School Express",                 // optional name
                Vehicle.VehicleType.VAN,
                15,
                "Toyota",
                "Quantum",
                2019,
                true,
                LocalDate.now().plusMonths(6),    // roadworthy valid
                "123456789",
                LocalDate.now().plusMonths(6),    // license disk valid
                "Santam",
                LocalDate.now().plusMonths(12),   // insurance valid
                mockDriver
        );

        assertNotNull(vehicle);
        assertEquals("CA123456", vehicle.getPlateNumber());
        assertEquals(Vehicle.VehicleType.VAN, vehicle.getVehicleType());
        assertEquals(15, vehicle.getCapacity());

        System.out.println("Vehicle created successfully: " + vehicle);
    }

    /* ============================
       FAILURE CASES
       ============================ */

    @Test
    void createVehicle_fail_invalidPlateNumber() {

        Vehicle vehicle = VehicleFactory.createVehicle(
                "123456789",
                "School Express",
                Vehicle.VehicleType.VAN,
                15,
                "Toyota",
                "Quantum",
                2019,
                true,
                LocalDate.now().plusMonths(6),
                "123456789",
                LocalDate.now().plusMonths(6),
                "Santam",
                LocalDate.now().plusMonths(12),
                mockDriver
        );

        assertNull(vehicle);
        System.out.println("Invalid plate number");
    }

    @Test
    void createVehicle_fail_invalidCapacity() {

        Vehicle vehicle = VehicleFactory.createVehicle(
                "CA123456",
                "School Express",
                Vehicle.VehicleType.SEDAN,
                0, // capacity should greater than zero.
                "Toyota",
                "Corolla",
                2020,
                true,
                LocalDate.now().plusMonths(6),
                "123456789",
                LocalDate.now().plusMonths(6),
                "Santam",
                LocalDate.now().plusMonths(12),
                mockDriver
        );

        assertNull(vehicle);
        System.out.println("Capacity should be greater than 0 && must be always be positive");
    }

    @Test
    void createVehicle_fail_invalidVehicleYear() {

        Vehicle vehicle = VehicleFactory.createVehicle(
                "CA123456",
                "School Express",
                Vehicle.VehicleType.VAN,
                15,
                "Toyota",
                "Quantum",
                1994, // too old
                true,
                LocalDate.now().plusMonths(6),
                "123456789",
                LocalDate.now().plusMonths(6),
                "Santam",
                LocalDate.now().plusMonths(12),
                mockDriver
        );

        assertNull(vehicle);
        System.out.println("The manufacturing year of the vehicle must be not in the future && not older than 30 years.");
    }

    @Test
    void createVehicle_fail_expiredRoadworthy() {

        Vehicle vehicle = VehicleFactory.createVehicle(
                "CA123456",
                "School Express",
                Vehicle.VehicleType.VAN,
                15,
                "Toyota",
                "Quantum",
                2019,
                true,
                LocalDate.now().minusDays(1), // expired
                "123456789",
                LocalDate.now().plusMonths(6),
                "Santam",
                LocalDate.now().plusMonths(12),
                mockDriver
        );

        assertNull(vehicle);
        System.out.println("If roadworthy is TRUE → expiry date is required and must be future\n" +
                "      If FALSE → expiry date may be null");
    }

    @Test
    void createVehicle_fail_expiredInsurance() {

        Vehicle vehicle = VehicleFactory.createVehicle(
                "CA123456",
                "School Express",
                Vehicle.VehicleType.VAN,
                15,
                "Toyota",
                "Quantum",
                2019,
                true,
                LocalDate.now().plusMonths(6),
                "123456789",
                LocalDate.now().plusMonths(6),
                "Santam",
                LocalDate.now().minusDays(1), // expired
                mockDriver
        );

        assertNull(vehicle);
        System.out.println("Insurance expiry date is required and must be future");
    }

    @Test
    void createVehicle_fail_inaValidDiskNumber() {

        Vehicle vehicle = VehicleFactory.createVehicle(
                "CA123456",
                "School Express",
                Vehicle.VehicleType.VAN,
                15,
                "Toyota",
                "Quantum",
                2019,
                true,
                LocalDate.now().plusMonths(6),
                "123456789",
                LocalDate.now().minusDays(1),
                "Santam",
                LocalDate.now().plusMonths(6), // expired
                mockDriver
        );

        assertNull(vehicle);
        System.out.println("The vehicle disk has expired or the disk number are invalid");

    }


}
