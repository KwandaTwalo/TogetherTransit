package com.kwndtwalo.TogetherTransit.controller.user;

import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.dto.generic.AddressDTO;
import com.kwndtwalo.TogetherTransit.dto.generic.ContactDTO;
import com.kwndtwalo.TogetherTransit.dto.users.DriverDTO;
import com.kwndtwalo.TogetherTransit.service.users.DriverService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    // =========================================
    // CREATE DRIVER (FULL REGISTRATION FLOW)
    // =========================================
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody DriverDTO dto,
            BindingResult result) {

        // 1. DTO validation
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        /*
         * BUSINESS RULE:
         * Driver creation is fully handled in SERVICE layer
         * including:
         * - Factory usage
         * - Duplicate checks
         * - Relationship attachment
         */
        Driver created = driverService.registerDriver(
                dto.getLicenseNumber(),
                dto.getRatingAverage(),
                dto.getFirstName(),
                dto.getLastName(),
                null, // status ignored, service enforces UNDER_REVIEW
                null, // Contact (can be added later)
                null, // Address
                null, // Authentication
                null // Role (service should assign default DRIVER role)
        );

        if (created == null) {
            return ResponseEntity.badRequest().body("Driver already exists or invalid data");
        }

        return ResponseEntity.ok(new DriverDTO(created));
    }

    // =========================================
    // READ DRIVER
    // =========================================
    @GetMapping("/read/{id}")
    public ResponseEntity<?> read(@PathVariable Long id) {

        Driver driver = driverService.read(id);

        if (driver == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DriverDTO(driver));
    }

    // =========================================
    // UPDATE DRIVER
    // =========================================
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody DriverDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Address address = null;

        // CONVERT ADDRESS DTO → ENTITY
        if (dto.getAddress() != null) {
            address = new Address.Builder()
                    .setStreetNumber(dto.getAddress().getStreetNumber())
                    .setStreetName(dto.getAddress().getStreetName())
                    .setSuburb(dto.getAddress().getSuburb())
                    .setCity(dto.getAddress().getCity())
                    .setPostalCode(dto.getAddress().getPostalCode())
                    .build();
        }

        Driver updatedDriver = new Driver.Builder()
                .setUserId(id)
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setLicenseNumber(dto.getLicenseNumber())
                .setRatingAverage(dto.getRatingAverage())
                //.setAccountStatus(dto.getAccountStatus())
                .setAddress(address) 
                .build();

        Driver updated = driverService.update(updatedDriver);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DriverDTO(updated));
    }

    // =========================================
    // GET ALL
    // =========================================
    @GetMapping("/getAllDrivers")
    public ResponseEntity<?> getAllDrivers() {

        List<DriverDTO> drivers = driverService.getAllDrivers()
                .stream()
                .map(DriverDTO::new)
                .toList();

        if (drivers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(drivers);
    }

    // =========================================
    // DELETE
    // =========================================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        boolean deleted = driverService.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Driver deleted successfully");
    }

    @PutMapping("/{id}/address")
    public ResponseEntity<?> attachAddress(
            @PathVariable Long id,
            @RequestBody AddressDTO dto) {

        Driver driver = driverService.read(id);

        if (driver == null) {
            return ResponseEntity.notFound().build();
        }

        Address address = new Address.Builder()
                .setStreetNumber(dto.getStreetNumber())
                .setStreetName(dto.getStreetName())
                .setSuburb(dto.getSuburb())
                .setCity(dto.getCity())
                .setPostalCode(dto.getPostalCode())
                .build();

        Driver updated = driverService.attachRelations(
                driver,
                driver.getContact(),
                address,
                driver.getAuthentication(),
                driver.getRole());

        return ResponseEntity.ok(new DriverDTO(updated));
    }

    @PutMapping("/{id}/contact")
    public ResponseEntity<?> attachContact(
            @PathVariable Long id,
            @Valid @RequestBody ContactDTO dto,
            BindingResult result) {

        // =========================================
        // 1. VALIDATE INPUT (DTO LEVEL)
        // =========================================
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // =========================================
        // 2. FIND EXISTING DRIVER
        // =========================================
        Driver driver = driverService.read(id);

        if (driver == null) {
            return ResponseEntity.notFound().build();
        }

        // =========================================
        // 3. CONVERT DTO → ENTITY
        // =========================================
        Contact contact = new Contact.Builder()
                .setPhoneNumber(dto.getPhoneNumber())
                .setEmergencyNumber(dto.getEmergencyNumber())
                .build();

        // =========================================
        // 4. ATTACH USING SERVICE LAYER
        // (IMPORTANT: DO NOT BUILD DRIVER HERE)
        // =========================================
        Driver updated = driverService.attachRelations(
                driver,
                contact,
                driver.getAddress(),
                driver.getAuthentication(),
                driver.getRole());

        // =========================================
        // 5. RETURN RESPONSE
        // =========================================
        return ResponseEntity.ok(new DriverDTO(updated));
    }
}