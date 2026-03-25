package com.kwndtwalo.TogetherTransit.controller.vehicle;

import com.kwndtwalo.TogetherTransit.domain.vehicle.Vehicle;
import com.kwndtwalo.TogetherTransit.dto.vehicle.VehicleDTO;
import com.kwndtwalo.TogetherTransit.service.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * CREATE VEHICLE
     * ------------------------------------------------------
     * This endpoint allows a driver or admin to register a new vehicle.
     *
     * Business Rules:
     * - Vehicle object must not be null
     * - Plate number must be unique (South African regulation)
     * - License disk number must also be unique
     * - If plate already exists, the system returns the existing vehicle
     *
     * Why we return DTO:
     * - Prevent exposing the entire Vehicle entity
     * - Avoid lazy-loading issues with relationships (Driver)
     */
    @PostMapping("/create")
    public ResponseEntity<VehicleDTO> create(@RequestBody Vehicle vehicle) {

        Vehicle createdVehicle = vehicleService.create(vehicle);

        if (createdVehicle == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(new VehicleDTO(createdVehicle));
    }

    @GetMapping("/read/{Id}")
    public ResponseEntity<VehicleDTO> read(@PathVariable Long Id) {
        Vehicle vehicle = vehicleService.read(Id);

        if (vehicle == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new VehicleDTO(vehicle));
    }

    @PutMapping("/update")
    public ResponseEntity<VehicleDTO> update(@RequestBody Vehicle vehicle) {

        Vehicle updatedVehicle = vehicleService.update(vehicle);

        if (updatedVehicle == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(new VehicleDTO(updatedVehicle));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long Id) {

        boolean deleted = vehicleService.delete(Id);

        return ResponseEntity.ok(deleted);
    }
}
