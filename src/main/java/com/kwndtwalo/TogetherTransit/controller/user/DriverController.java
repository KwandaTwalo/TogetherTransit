package com.kwndtwalo.TogetherTransit.controller.user;

import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.dto.users.DriverDTO;
import com.kwndtwalo.TogetherTransit.service.users.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    /*
     * CREATE
     * -----------------------------------------
     * Purpose:
     * Creates a new driver account or returns existing one.
     *
     * Business Rules:
     * - Prevents duplicate drivers using:
     *   firstName + lastName uniqueness check.
     *
     * Returns:
     * - 200 OK → Driver successfully created or already exists
     * - 400 Bad Request → Invalid input data
     */
    @PostMapping("/create")
    public ResponseEntity<DriverDTO> create(@RequestBody Driver driver) {

        Driver created = driverService.create(driver);

        if (created == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new DriverDTO(created));
    }

    /*
     * READ BY ID
     * -----------------------------------------
     * Purpose:
     * Retrieves a driver by their unique ID.
     *
     * Returns:
     * - 200 OK → Driver found
     * - 404 Not Found → Driver does not exist
     */
    @GetMapping("/read/{id}")
    public ResponseEntity<DriverDTO> read(@PathVariable Long id) {

        Driver driver = driverService.read(id);

        if (driver == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DriverDTO(driver));
    }

    /*
     * UPDATE
     * -----------------------------------------
     * Purpose:
     * Updates an existing driver profile.
     *
     * Business Rules:
     * - Driver must already exist.
     *
     * Returns:
     * - 200 OK → Driver successfully updated
     * - 404 Not Found → Driver does not exist
     */
    @PutMapping("/update")
    public ResponseEntity<DriverDTO> update(@RequestBody Driver driver) {

        Driver updated = driverService.update(driver);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DriverDTO(updated));
    }

    /*
     * GET ALL DRIVERS
     * -----------------------------------------
     * Purpose:
     * Fetches all registered drivers in the system.
     *
     * Returns:
     * - 200 OK → List of drivers
     * - 204 No Content → No drivers exist
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<DriverDTO>> getAllDrivers() {

        List<Driver> drivers = driverService.getAllDrivers();

        if (drivers == null || drivers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<DriverDTO> dtoList = drivers.stream()
                .map(DriverDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    /*
     * DELETE
     * -----------------------------------------
     * Purpose:
     * Deletes a driver account using ID.
     *
     * Business Rules:
     * - Only deletes if record exists.
     *
     * Returns:
     * - 200 OK → Deleted successfully
     * - 404 Not Found → Driver does not exist
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        boolean deleted = driverService.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
}