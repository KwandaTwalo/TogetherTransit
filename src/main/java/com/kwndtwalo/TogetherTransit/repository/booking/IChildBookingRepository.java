package com.kwndtwalo.TogetherTransit.repository.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.booking.ChildBooking;
import com.kwndtwalo.TogetherTransit.domain.child.Child;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface IChildBookingRepository extends JpaRepository<ChildBooking, Long> {

    // ---------------------------------
    // BOOKING-BASED QUERIES
    // ---------------------------------

    // Fetch all child bookings under a single booking
    List<ChildBooking> findByBooking(Booking booking);

    // Fetch child bookings for a booking by status
    List<ChildBooking> findByBookingAndChildBookingStatus(
            Booking booking,
            ChildBooking.ChildBookingStatus status
    );


    // ---------------------------------
    // CHILD-BASED QUERIES
    // ---------------------------------

    // Fetch all bookings for a specific child
    List<ChildBooking> findByChild(Child child);

    // Fetch active booking for a child
    Optional<ChildBooking> findByChildAndChildBookingStatus(
            Child child,
            ChildBooking.ChildBookingStatus status
    );


    // ---------------------------------
    // ROUTE-BASED QUERIES
    // ---------------------------------

    // Fetch all child bookings using a specific route
    List<ChildBooking> findByRoute(Route route);

    // Fetch active child bookings per route
    List<ChildBooking> findByRouteAndChildBookingStatus(
            Route route,
            ChildBooking.ChildBookingStatus status
    );


    // ---------------------------------
    // CUSTOM SCHEDULE QUERIES
    // ---------------------------------

    // Fetch children that use custom schedules
    List<ChildBooking> findByUsesCustomScheduleTrue();

    // Fetch child bookings that include a specific day
    List<ChildBooking> findByCustomDaysContaining(DayOfWeek day);


    // ---------------------------------
    // VALIDATION / DUPLICATE PREVENTION
    // ---------------------------------

    // Prevent duplicate child-booking for same child under same booking
    boolean existsByBookingAndChild(Booking booking, Child child);

}
