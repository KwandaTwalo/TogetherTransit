package com.kwndtwalo.TogetherTransit.factory.vehicle;

import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.vehicle.Vehicle;
import com.kwndtwalo.TogetherTransit.util.Helper;

import java.time.LocalDate;

public class VehicleFactory {

    public static Vehicle createVehicle(String plateNumber, String vehicleName, Vehicle.VehicleType vehicleType,
                                        int capacity, String make, String model, int year,
                                        boolean roadWorthyStatus, LocalDate roadWorthyExpiryDate,
                                        String licenseDiskNumber, LocalDate licenseExpiryDate, String insuranceProvider,
                                        LocalDate insuranceExpiryDate, Driver driver) {

        if (!Helper.isValidPlateNumber(plateNumber) || !Helper.isValidString(vehicleName)
                || !Helper.isValidVehicleCapacity(capacity) || !Helper.isValidString(make)
                || !Helper.isValidString(model) || !Helper.isValidVehicleYear(year)
                || !Helper.isValidRoadworthy(roadWorthyStatus, roadWorthyExpiryDate)
                || !Helper.isValidLicenseDisk(licenseDiskNumber, licenseExpiryDate)
                || !Helper.isValidInsurance(insuranceProvider, insuranceExpiryDate)) {
            return null;
        }

        return new Vehicle.Builder()
                .setPlateNumber(plateNumber)
                .setVehicleName(vehicleName)
                .setVehicleType(vehicleType)
                .setCapacity(capacity)
                .setMake(make)
                .setModel(model)
                .setYear(year)
                .setRoadworthyStatus(roadWorthyStatus)
                .setRoadworthyExpiryDate(roadWorthyExpiryDate)
                .setLicenseDiskNumber(licenseDiskNumber)
                .setLicenseExpiryDate(licenseExpiryDate)
                .setInsuranceProvider(insuranceProvider)
                .setInsuranceExpiryDate(insuranceExpiryDate)
                .setDriver(driver)
                .build();
    }
}
