package com.kwndtwalo.TogetherTransit.dto.route;

import com.kwndtwalo.TogetherTransit.domain.route.Route;

import java.time.DayOfWeek;
import java.util.Set;

public class RouteDTO {

    private Long routeId;
    private String pickupPoint;
    private String dropoffPoint;
    private boolean monthlyContract;
    private double monthlyFee;
    private double estimatedDistanceKm;
    private double estimatedDurationMinutes;
    private Set<DayOfWeek> serviceDays;
    private Route.RouteStatus status;
    private String specialInstructions;

    private Long driverId;
    private Long schoolId;

    public RouteDTO(Route route) {
        this.routeId = route.getRouteId();
        this.pickupPoint = route.getPickupPoint();
        this.dropoffPoint = route.getDropoffPoint();
        this.monthlyContract = route.getIsMonthlyContract();
        this.monthlyFee = route.getMonthlyFee();
        this.estimatedDistanceKm = route.getEstimatedDistanceKm();
        this.estimatedDurationMinutes = route.getEstimatedDurationMinutes();
        this.serviceDays = route.getServiceDays();
        this.status = route.getStatus();
        this.driverId = route.getDriver() != null ? route.getDriver().getUserId() : null;
        this.schoolId = route.getSchool() != null ? route.getSchool().getSchoolId() : null;
        this.specialInstructions = route.getSpecialInstructions();
    }

    @Override
    public String toString() {
        return "RouteDTO{" +
                "routeId=" + routeId +
                ", pickupPoint='" + pickupPoint + '\'' +
                ", dropoffPoint='" + dropoffPoint + '\'' +
                ", isMonthlyContract=" + monthlyContract +
                ", monthlyFee=" + monthlyFee +
                ", estimatedDistanceKm=" + estimatedDistanceKm +
                ", estimatedDurationMinutes=" + estimatedDurationMinutes +
                ", serviceDays=" + serviceDays +
                ", status=" + status +
                ", driverId=" + driverId +
                ", schoolId=" + schoolId +
                ", specialInstructions='" + specialInstructions + '\'' +
                '}';
    }

    // getters only (DTOs are immutable)
}
