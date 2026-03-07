package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.repository.users.IDriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService implements IDriverService {

    private IDriverRepository driverRepository;

    @Autowired
    public DriverService(IDriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    /*
     * Create or return existing driver.
     * Business rule:
     * Same first name + last name = same driver (duplicate prevention).
     */
    @Override
    public Driver create(Driver driver) {
        if (driver == null) {
            return null;
        }

        return driverRepository
                .findByFirstNameAndLastName(
                        driver.getFirstName(),
                        driver.getLastName()
                )
                .orElseGet(() -> driverRepository.save(driver));
    }

    /*
     * Read a driver by ID.
     * Returns null if not found (service-layer safety).
     */
    @Override
    public Driver read(Long Id) {
        return driverRepository.findById(Id).orElse(null);
    }

    /*
     * Update an existing driver.
     * Business rule:
     * You cannot update a driver that does not exist.
     */
    @Override
    public Driver update(Driver driver) {
        if (driver == null || driver.getUserId() == null) {
            return null;
        }

        if (!driverRepository.existsById(driver.getUserId())) {
            return null;
        }
        return driverRepository.save(driver);
    }

    /*
     * Delete a driver by ID.
     * Returns true only if deletion actually happened.
     */
    @Override
    public boolean delete(Long Id) {
        if(!driverRepository.existsById(Id)) {
            return false;
        }
        driverRepository.deleteById(Id);
        return true;
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }
}
