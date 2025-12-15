package com.kwndtwalo.TogetherTransit.domain.route;

import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;

    private String pickupPoint;
    private String dropoffPoint;

    // Monthly subscription
    private boolean monthlyContract;
    private double monthlyFee;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;

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
    private List<ExamSchedule> examSchedules = new ArrayList<>();

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
}

