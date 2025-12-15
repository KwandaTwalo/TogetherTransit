package com.kwndtwalo.TogetherTransit.domain.booking;

import com.kwndtwalo.TogetherTransit.domain.child.Child;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ChildBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long childBookingId;

    // Link to main booking
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookingId")
    private Booking booking;

    // The child who is included in the booking
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "childId")
    private Child child;

    // Optional override for specific child
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routeId")
    private Route route;

    // Custom schedule for this child
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "child_booking_days", joinColumns = @JoinColumn(name = "childBookingId"))
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private Set<DayOfWeek> customDays = new HashSet<>();

    protected ChildBooking() {}
}
