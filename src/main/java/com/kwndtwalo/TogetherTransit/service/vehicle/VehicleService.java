package com.kwndtwalo.TogetherTransit.service.vehicle;

import com.kwndtwalo.TogetherTransit.domain.vehicle.Vehicle;
import com.kwndtwalo.TogetherTransit.repository.vehicle.IVehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService implements IVehicleService {

    private IVehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(IVehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }


    /*
     * CREATE
     * Business rules:
     * - Vehicle must not be null
     * - Plate number must be unique
     * - License disk number must be unique
     */
    @Override
    public Vehicle create(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        //Prevent duplicate plate number.
        if (vehicleRepository.existsByPlateNumber(vehicle.getPlateNumber())) {
            return vehicleRepository
                    .findByPlateNumber(vehicle.getPlateNumber())
                    .orElse(null);
        }

        //Prevent duplicate license disk number.
        if (vehicleRepository.existsByLicenseDiskNumber(vehicle.getLicenseDiskNumber())) {
            return null;
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle read(Long Id) {
        return vehicleRepository.findById(Id).orElse(null);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        if (vehicle == null || vehicle.getVehicleId() == null) {
            return null;
        }
        if (!vehicleRepository.existsById(vehicle.getVehicleId())) {
            return null;
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public boolean delete(Long Id) {
        if(!vehicleRepository.existsById(Id)) {
            return false;
        }
        vehicleRepository.deleteById(Id);
        return true;
    }
}
