package com.kwndtwalo.TogetherTransit.domain.route;

/**Route is the core transport unit that controls:
 * Pickup & drop-off points
 * Driver assignment
 * School assignment
 * Monthly pricing
 * Service days (Mon–Sun)
 * Status lifecycle
 * Distance & duration estimates
 * Scheduling (time slots & exam overrides)
 * */

import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Route {

    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;

    private String pickupPoint;
    private String dropoffPoint;

    // Monthly subscription
    private boolean isMonthlyContract;
    private double monthlyFee;

    // Coordinates
    private Double pickupLatitude;
    private Double pickupLongitude;
    private Double dropoffLatitude;
    private Double dropoffLongitude;

    private double estimatedDistanceKm;
    private double estimatedDurationMinutes;

    // Monday–Sunday supported
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "route_service_days",
            joinColumns = @JoinColumn(name = "routeId")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private Set<DayOfWeek> serviceDays = new HashSet<>();

    // Multiple time slots
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RouteTimeSlot> timeSlots = new ArrayList<>();

    // Exam overrides
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamPeriod> examPeriods = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RouteStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driverId")
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schoolId")
    private School school;

    private String specialInstructions;

    public enum RouteStatus { SCHEDULED, ACTIVE, COMPLETED, CANCELLED }

    //CONSTRUCTORS
    protected Route() {}

    private Route(Builder builder) {
        this.routeId = builder.routeId;
        this.pickupPoint = builder.pickupPoint;
        this.dropoffPoint = builder.dropoffPoint;
        this.isMonthlyContract = builder.isMonthlyContract;
        this.monthlyFee = builder.monthlyFee;
        this.pickupLatitude = builder.pickupLatitude;
        this.pickupLongitude = builder.pickupLongitude;
        this.dropoffLatitude = builder.dropoffLatitude;
        this.dropoffLongitude = builder.dropoffLongitude;
        this.estimatedDistanceKm = builder.estimatedDistanceKm;
        this.estimatedDurationMinutes = builder.estimatedDurationMinutes;
        this.status = builder.status;
        this.serviceDays = builder.serviceDays;
        this.timeSlots = builder.timeSlots;
        this.examPeriods = builder.examPeriods;
        this.status = builder.status;
        this.driver = builder.driver;
        this.school = builder.school;
        this.specialInstructions = builder.specialInstructions;
    }

    //GETTERS
    public Long getRouteId() {
        return routeId;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }

    public String getDropoffPoint() {
        return dropoffPoint;
    }

    public boolean getIsMonthlyContract() {
        return isMonthlyContract;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public Double getPickupLatitude() {
        return pickupLatitude;
    }

    public Double getPickupLongitude() {
        return pickupLongitude;
    }

    public Double getDropoffLatitude() {
        return dropoffLatitude;
    }

    public Double getDropoffLongitude() {
        return dropoffLongitude;
    }

    public double getEstimatedDistanceKm() {
        return estimatedDistanceKm;
    }

    public double getEstimatedDurationMinutes() {
        return estimatedDurationMinutes;
    }

    public Set<DayOfWeek> getServiceDays() {
        return serviceDays;
    }

    public List<RouteTimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public List<ExamPeriod> getExamPeriods() {
        return examPeriods;
    }

    public RouteStatus getStatus() {
        return status;
    }

    public Driver getDriver() {
        return driver;
    }

    public School getSchool() {
        return school;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + getRouteId() +
                ", pickupPoint='" + getPickupPoint() + '\'' +
                ", dropoffPoint='" + getDropoffPoint() + '\'' +
                ", isMonthlyContract=" + getIsMonthlyContract() +
                ", monthlyFee=" + getMonthlyFee() +
                ", pickupLatitude=" + getPickupLatitude() +
                ", pickupLongitude=" + getPickupLongitude() +
                ", dropoffLatitude=" + getDropoffLatitude() +
                ", dropoffLongitude=" + getDropoffLongitude() +
                ", estimatedDistanceKm=" + getEstimatedDistanceKm() +
                ", estimatedDurationMinutes=" + getEstimatedDurationMinutes() +
                ", serviceDays=" + getServiceDays() +
                ", timeSlots=" + getTimeSlots() +
                ", examPeriods=" + getExamPeriods() +
                ", status=" + getStatus() +
                ", driver=" + getDriver() +
                ", school=" + getSchool() +
                ", specialInstructions='" + getSpecialInstructions() + '\'' +
                '}';
    }

    public static class Builder {
        private Long routeId;
        private String pickupPoint;
        private String dropoffPoint;
        private boolean isMonthlyContract;
        private Double monthlyFee;
        private Double pickupLatitude;
        private Double pickupLongitude;
        private Double dropoffLatitude;
        private Double dropoffLongitude;
        private Double estimatedDistanceKm;
        private Double estimatedDurationMinutes;
        private Set<DayOfWeek> serviceDays;
        private List<RouteTimeSlot> timeSlots;
        private List<ExamPeriod> examPeriods;
        private RouteStatus status;
        private Driver driver;
        private School school;
        private String specialInstructions;

        public Builder setRouteId(Long routeId) {
            this.routeId = routeId;
            return this;
        }
        public Builder setPickupPoint(String pickupPoint) {
            this.pickupPoint = pickupPoint;
            return this;
        }
        public Builder setDropoffPoint(String dropoffPoint) {
            this.dropoffPoint = dropoffPoint;
            return this;
        }
        public Builder setIsMonthlyContract(boolean isMonthlyContract) {
            this.isMonthlyContract = isMonthlyContract;
            return this;
        }
        public Builder setMonthlyFee(Double monthlyFee) {
            this.monthlyFee = monthlyFee;
            return this;
        }
        public Builder setPickupLatitude(Double pickupLatitude) {
            this.pickupLatitude = pickupLatitude;
            return this;
        }
        public Builder setPickupLongitude(Double pickupLongitude) {
            this.pickupLongitude = pickupLongitude;
            return this;
        }
        public Builder setDropoffLatitude(Double dropoffLatitude) {
            this.dropoffLatitude = dropoffLatitude;
            return this;
        }
        public Builder setDropoffLongitude(Double dropoffLongitude) {
            this.dropoffLongitude = dropoffLongitude;
            return this;
        }
        public Builder setEstimatedDistanceKm(Double estimatedDistanceKm) {
            this.estimatedDistanceKm = estimatedDistanceKm;
            return this;
        }
        public Builder setEstimatedDurationMinutes(Double estimatedDurationMinutes) {
            this.estimatedDurationMinutes = estimatedDurationMinutes;
            return this;
        }
        public Builder setServiceDays(Set<DayOfWeek> serviceDays) {
            this.serviceDays = serviceDays;
            return this;
        }
        public Builder setTimeSlots(List<RouteTimeSlot> timeSlots) {
            this.timeSlots = timeSlots;
            return this;
        }
        public Builder setExamPeriods(List<ExamPeriod> examPeriods) {
            this.examPeriods = examPeriods;
            return this;
        }
        public Builder setStatus(RouteStatus status) {
            this.status = status;
            return this;
        }
        public Builder setDriver(Driver driver) {
            this.driver = driver;
            return this;
        }
        public Builder setSchool(School school) {
            this.school = school;
            return this;
        }
        public Builder setSpecialInstructions(String specialInstructions) {
            this.specialInstructions = specialInstructions;
            return this;
        }

        public Builder copy(Route route) {
            this.routeId = route.getRouteId();
            this.pickupPoint = route.getPickupPoint();
            this.dropoffPoint = route.getDropoffPoint();
            this.isMonthlyContract = route.getIsMonthlyContract();
            this.monthlyFee = route.getMonthlyFee();
            this.pickupLatitude = route.getPickupLatitude();
            this.pickupLongitude = route.getPickupLongitude();
            this.dropoffLatitude = route.getDropoffLatitude();
            this.dropoffLongitude = route.getDropoffLongitude();
            this.estimatedDistanceKm = route.getEstimatedDistanceKm();
            this.estimatedDurationMinutes = route.getEstimatedDurationMinutes();
            this.serviceDays = route.getServiceDays();
            this.timeSlots = route.getTimeSlots();
            this.examPeriods = route.getExamPeriods();
            this.status = route.getStatus();
            this.driver = route.getDriver();
            this.school = route.getSchool();
            this.specialInstructions = route.getSpecialInstructions();
            return this;
        }

        public Route build() {return new Route(this);}
    }
}

