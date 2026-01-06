package com.kwndtwalo.TogetherTransit.repository.vehicle;

import com.kwndtwalo.TogetherTransit.domain.vehicle.Vehicle;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.vehicle.Vehicle.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, Long> {

    // ---------------------------------
    // UNIQUE & DUPLICATE PREVENTION
    // ---------------------------------

    // SA plate numbers must be unique
    Optional<Vehicle> findByPlateNumber(String plateNumber);

    boolean existsByPlateNumber(String plateNumber);

    // License disk numbers must also be unique
    boolean existsByLicenseDiskNumber(String licenseDiskNumber);


    // ---------------------------------
    // DRIVER OWNERSHIP
    // ---------------------------------

    // All vehicles owned by a driver
    List<Vehicle> findByDriver(Driver driver);

    // Active vehicles for a driver (roadworthy only)
    List<Vehicle> findByDriverAndRoadworthyStatusTrue(Driver driver);


    // ---------------------------------
    // VEHICLE TYPE & CAPACITY
    // ---------------------------------

    // Used when matching vehicle to route capacity
    List<Vehicle> findByVehicleType(VehicleType vehicleType);

    List<Vehicle> findByCapacityGreaterThanEqual(int capacity);


    // ---------------------------------
    // COMPLIANCE & SAFETY CHECKS
    // ---------------------------------

    // Vehicles with expired roadworthy certificates
    List<Vehicle> findByRoadworthyExpiryDateBefore(LocalDate date);

    // Vehicles with expired license disks
    List<Vehicle> findByLicenseExpiryDateBefore(LocalDate date);

    // Vehicles with expired insurance
    List<Vehicle> findByInsuranceExpiryDateBefore(LocalDate date);


    // ---------------------------------
    // UPCOMING EXPIRY ALERTS (ADMIN / DRIVER)
    // ---------------------------------

    // Roadworthy expiring soon (e.g., next 30 days)
    List<Vehicle> findByRoadworthyExpiryDateBetween(
            LocalDate start,
            LocalDate end
    );

    // License disk expiring soon
    List<Vehicle> findByLicenseExpiryDateBetween(
            LocalDate start,
            LocalDate end
    );

    // Insurance expiring soon
    List<Vehicle> findByInsuranceExpiryDateBetween(
            LocalDate start,
            LocalDate end
    );


    // ---------------------------------
    // OPERATIONAL READINESS
    // ---------------------------------

    // Vehicles allowed to operate (all valid)
    List<Vehicle> findByRoadworthyStatusTrueAndLicenseExpiryDateAfterAndInsuranceExpiryDateAfter(
            LocalDate today,
            LocalDate today2
    );
}
