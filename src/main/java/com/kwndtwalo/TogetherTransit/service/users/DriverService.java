package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.factory.users.DriverFactory;
import com.kwndtwalo.TogetherTransit.repository.users.IDriverRepository;
import com.kwndtwalo.TogetherTransit.repository.users.IRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService implements IDriverService {

    private final IDriverRepository driverRepository;
    private final IRoleRepository roleRepository;

    @Autowired
    public DriverService(IDriverRepository driverRepository, IRoleRepository roleRepository) {
        this.driverRepository = driverRepository;
        this.roleRepository = roleRepository;
    }

    // =========================================================
    // BUSINESS RULE:
    // Prevent duplicate drivers using license number (primary identifier)
    // =========================================================
    @Override
    public Driver create(Driver driver) {

        if (driver == null) {
            return null;
        }

        Optional<Driver> existingDriver = driverRepository.findByLicenseNumber(driver.getLicenseNumber());

        if (existingDriver.isPresent()) {
            return null; // business rule: no duplicates allowed
        }

        return driverRepository.save(driver);
    }

    // =========================================================
    // BUSINESS RULE:
    // Read driver safely by ID
    // =========================================================
    @Override
    public Driver read(Long id) {
        return driverRepository.findById(id).orElse(null);
    }

    // =========================================================
    // BUSINESS RULE:
    // Update only existing drivers
    // Must preserve relationships unless explicitly changed
    // =========================================================
    @Override
    public Driver update(Driver updatedDriver) {

        if (updatedDriver == null || updatedDriver.getUserId() == null) {
            return null;
        }

        Driver existing = driverRepository.findById(updatedDriver.getUserId())
                .orElse(null);

        if (existing == null) {
            return null; // cannot update non-existent driver
        }

        // Use Builder COPY pattern to preserve existing state
        Driver merged = new Driver.Builder()
                .copy(existing)
                .setFirstName(updatedDriver.getFirstName())
                .setLastName(updatedDriver.getLastName())
                .setLicenseNumber(updatedDriver.getLicenseNumber())
                .setRatingAverage(updatedDriver.getRatingAverage())
                //.setAccountStatus(updatedDriver.getAccountStatus())
                .setContact(updatedDriver.getContact())
                .setAddress(updatedDriver.getAddress())
                .setAuthentication(updatedDriver.getAuthentication())
                .setRole(existing.getRole()) //Preserving the existing role unless explicitly changed (you can add logic to change it if needed)
                .build();

        return driverRepository.save(merged);
    }

    // =========================================================
    // BUSINESS RULE:
    // Safe delete with existence check
    // =========================================================
    @Override
    public boolean delete(Long id) {

        if (!driverRepository.existsById(id)) {
            return false;
        }

        driverRepository.deleteById(id);
        return true;
    }

    // =========================================================
    // BUSINESS RULE:
    // Return all drivers (admin use case)
    // =========================================================
    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    // =========================================================
    // BUSINESS RULE:
    // Attach relationships AFTER driver creation
    // (THIS is where your Contact/Address/Auth/Role belong)
    // =========================================================
    public Driver attachRelations(
            Driver driver,
            Contact contact,
            Address address,
            Authentication authentication,
            Role role) {

        if (driver == null) {
            return null;
        }

        Driver updated = new Driver.Builder()
                .copy(driver)
                .setContact(contact)
                .setAddress(address)
                .setAuthentication(authentication)
                .setRole(role)
                .build();

        return driverRepository.save(updated);
    }

    // =========================================================
    // BUSINESS RULE:
    // Full registration flow (used by controller)
    // Keeps controller clean
    // =========================================================
    public Driver registerDriver(
            String licenseNumber,
            int ratingAverage,
            String firstName,
            String lastName,
            User.AccountStatus status,
            Contact contact,
            Address address,
            Authentication authentication,
            Role role) {

        // Enforcing defualts
        User.AccountStatus enforcedStatus = User.AccountStatus.UNDER_REVIEW;

        // Fetch persisted role from DB
        Role enforcedRole = roleRepository.findByRoleName(Role.RoleName.DRIVER)
                .orElseThrow(() -> new IllegalStateException("Default DRIVER role not found in database"));

        Driver driver = DriverFactory.createDriver(
                licenseNumber,
                ratingAverage,
                firstName,
                lastName,
                java.time.LocalDate.now(),
                enforcedStatus); // Enforce UNDER_REVIEW status on registration

        if (driver == null) {
            return null;
        }

        Driver enriched = attachRelations(
                driver,
                contact,
                address,
                authentication,
                enforcedRole);

        return driverRepository.save(enriched);
    }
}