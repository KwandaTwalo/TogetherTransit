package com.kwndtwalo.TogetherTransit.domain.booking;

import com.kwndtwalo.TogetherTransit.domain.child.Child;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    // The parent who creates the booking
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Parent parent;

    // The route being booked (includes driver, timeslots, school)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routeId")
    private Route route;

    // All children included in this booking (siblings allowed)
    @ManyToMany
    @JoinTable(
            name = "booking_children",
            joinColumns = @JoinColumn(name = "bookingId"),
            inverseJoinColumns = @JoinColumn(name = "childId")
    )
    private Set<Child> children = new HashSet<>();

    private LocalDate bookingDate;        // When booking was made
    private LocalDate startDate;          // When the service begins
    private LocalDate endDate;            // Normally end of month or custom

    @Enumerated(EnumType.STRING)
    private BookingStatus status;         // ACTIVE, CANCELLED, PENDING, COMPLETED

    private double totalAmount;           // Calculated from route.monthlyFee

    public enum BookingStatus {
        PENDING,
        ACTIVE,
        COMPLETED,
        CANCELLED
    }

    protected Booking() {}
}
