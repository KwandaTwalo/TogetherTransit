package com.kwndtwalo.TogetherTransit.dto.vehicle;

import com.kwndtwalo.TogetherTransit.domain.vehicle.Vehicle;

import java.time.LocalDate;

public class VehicleDTO {

    private Long vehicleId;
    private String plateNumber;
    private String vehicleName;
    private String vehicleType;
    private int capacity;

    private String make;
    private String model;
    private int year;

    private boolean roadworthyStatus;
    private LocalDate roadworthyExpiryDate;

    private String licenseDiskNumber;
    private LocalDate licenseExpiryDate;

    private String insuranceProvider;
    private LocalDate insuranceExpiryDate;

    // Relations (safe IDs only)
    private Long driverId;

    public VehicleDTO(Vehicle vehicle) {
        this.vehicleId = vehicle.getVehicleId();
        this.plateNumber = vehicle.getPlateNumber();
        this.vehicleName = vehicle.getVehicleName();
        this.vehicleType = vehicle.getVehicleType().name();
        this.capacity = vehicle.getCapacity();

        this.make = vehicle.getMake();
        this.model = vehicle.getModel();
        this.year = vehicle.getYear();

        this.roadworthyStatus = vehicle.getIsRoadworthyStatus();
        this.roadworthyExpiryDate = vehicle.getRoadworthyExpiryDate();

        this.licenseDiskNumber = vehicle.getLicenseDiskNumber();
        this.licenseExpiryDate = vehicle.getLicenseExpiryDate();

        this.insuranceProvider = vehicle.getInsuranceProvider();
        this.insuranceExpiryDate = vehicle.getInsuranceExpiryDate();

        // Lazy-safe relation
        this.driverId = vehicle.getDriver() != null
                ? vehicle.getDriver().getUserId()
                : null;
    }

    @Override
    public String toString() {
        return "\nVehicleDTO{" +
                "vehicleId=" + vehicleId +
                ", plateNumber='" + plateNumber + '\'' +
                ", vehicleName='" + vehicleName + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", capacity=" + capacity +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", roadworthyStatus=" + roadworthyStatus +
                ", roadworthyExpiryDate=" + roadworthyExpiryDate +
                ", licenseDiskNumber='" + licenseDiskNumber + '\'' +
                ", licenseExpiryDate=" + licenseExpiryDate +
                ", insuranceProvider='" + insuranceProvider + '\'' +
                ", insuranceExpiryDate=" + insuranceExpiryDate +
                ", driverId=" + driverId +
                '}';
    }
}