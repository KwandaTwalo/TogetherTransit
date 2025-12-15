package com.kwndtwalo.TogetherTransit.domain.vehicle;

import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    private String plateNumber;
    private String vehicleName;      // Optional nickname e.g. “School Express”
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    private int capacity;

    // Make/model info
    private String make;
    private String model;
    private int year;

    // Legal compliance
    private boolean roadworthyStatus;
    private LocalDate roadworthyExpiryDate;

    private String licenseDiskNumber;
    private LocalDate licenseExpiryDate;

    // Insurance
    private String insuranceProvider;
    private LocalDate insuranceExpiryDate;

    public enum VehicleType {
        VAN,
        SEDAN,
        MINIBUS,
        BUS
    }

    // Relationship to driver
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driverId")
    private Driver driver;

    protected Vehicle() {}

    private Vehicle(Builder builder) {
        this.vehicleId = builder.vehicleId;
        this.plateNumber = builder.plateNumber;
        this.vehicleName = builder.vehicleName;
        this.vehicleType = builder.vehicleType;
        this.capacity = builder.capacity;
        this.make = builder.make;
        this.model = builder.model;
        this.year = builder.year;
        this.roadworthyStatus = builder.roadworthyStatus;
        this.roadworthyExpiryDate = builder.roadworthyExpiryDate;
        this.licenseDiskNumber = builder.licenseDiskNumber;
        this.licenseExpiryDate = builder.licenseExpiryDate;
        this.insuranceProvider = builder.insuranceProvider;
        this.insuranceExpiryDate = builder.insuranceExpiryDate;
        this.driver = builder.driver;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public boolean getIsRoadworthyStatus() {
        return roadworthyStatus;
    }

    public LocalDate getRoadworthyExpiryDate() {
        return roadworthyExpiryDate;
    }

    public String getLicenseDiskNumber() {
        return licenseDiskNumber;
    }

    public LocalDate getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public LocalDate getInsuranceExpiryDate() {
        return insuranceExpiryDate;
    }

    public Driver getDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + getVehicleId() +
                ", plateNumber='" + getPlateNumber() + '\'' +
                ", vehicleName='" + getVehicleName() + '\'' +
                ", vehicleType=" + getVehicleType() +
                ", capacity=" + getCapacity() +
                ", make='" + getMake() + '\'' +
                ", model='" + getModel() + '\'' +
                ", year=" + getYear() +
                ", roadworthyStatus=" + getIsRoadworthyStatus() +
                ", roadworthyExpiryDate=" + getRoadworthyExpiryDate() +
                ", licenseDiskNumber='" + getLicenseDiskNumber() + '\'' +
                ", licenseExpiryDate=" + getLicenseExpiryDate() +
                ", insuranceProvider='" + getInsuranceProvider() + '\'' +
                ", insuranceExpiryDate=" + getInsuranceExpiryDate() +
                ", driver=" + getDriver() +
                '}';
    }

    public static class Builder {
        private Long vehicleId;
        private String plateNumber;
        private String vehicleName;
        private VehicleType vehicleType;
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
        private Driver driver;

        public Builder setVehicleId(Long vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }
        public Builder setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
            return this;
        }
        public Builder setVehicleName(String vehicleName) {
            this.vehicleName = vehicleName;
            return this;
        }
        public Builder setVehicleType(VehicleType vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }
        public Builder setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }
        public Builder setMake(String make) {
            this.make = make;
            return this;
        }
        public Builder setModel(String model) {
            this.model = model;
            return this;
        }
        public Builder setYear(int year) {
            this.year = year;
            return this;
        }
        public Builder setRoadworthyStatus(boolean roadworthyStatus) {
            this.roadworthyStatus = roadworthyStatus;
            return this;
        }
        public Builder setRoadworthyExpiryDate(LocalDate roadworthyExpiryDate) {
            this.roadworthyExpiryDate = roadworthyExpiryDate;
            return this;
        }
        public Builder setLicenseDiskNumber(String licenseDiskNumber) {
            this.licenseDiskNumber = licenseDiskNumber;
            return this;
        }
        public Builder setLicenseExpiryDate(LocalDate licenseExpiryDate) {
            this.licenseExpiryDate = licenseExpiryDate;
            return this;
        }
        public Builder setInsuranceProvider(String insuranceProvider) {
            this.insuranceProvider = insuranceProvider;
            return this;
        }
        public Builder setInsuranceExpiryDate(LocalDate insuranceExpiryDate) {
            this.insuranceExpiryDate = insuranceExpiryDate;
            return this;
        }
        public Builder setDriver(Driver driver) {
            this.driver = driver;
            return this;
        }

        public Builder copy(Vehicle vehicle) {
            this.vehicleId = vehicle.getVehicleId();
            this.plateNumber = vehicle.getPlateNumber();
            this.vehicleName = vehicle.getVehicleName();
            this.vehicleType = vehicle.getVehicleType();
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
            this.driver = vehicle.getDriver();
            return this;
        }

        public Vehicle build() {return new Vehicle(this);}
    }
}

