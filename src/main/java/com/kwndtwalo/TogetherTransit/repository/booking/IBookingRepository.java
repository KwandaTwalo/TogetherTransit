package com.kwndtwalo.TogetherTransit.repository.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {

    // ---------------------------------
    // PARENT-BASED QUERIES
    // ---------------------------------

    // Fetch all bookings made by a parent
    List<Booking> findByParent(Parent parent);

    // Fetch bookings by parent and status (ACTIVE, CANCELLED, etc.)
    List<Booking> findByParentAndStatus(Parent parent, Booking.BookingStatus status);

    // Fetch parent’s current active booking
    Optional<Booking> findFirstByParentAndStatus(
            Parent parent,
            Booking.BookingStatus status
    );


    // ---------------------------------
    // STATUS & LIFECYCLE
    // ---------------------------------

    // Fetch all bookings by status
    List<Booking> findByStatus(Booking.BookingStatus status);

    // Check if a booking is ACTIVE (used before payment / rating)
    boolean existsByBookingIdAndStatus(Long bookingId, Booking.BookingStatus status);


    // ---------------------------------
    // DATE-BASED QUERIES
    // ---------------------------------

    // Find bookings starting within a date range
    List<Booking> findByContractStartDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );

    // Find bookings active on a specific date
    List<Booking> findByContractStartDateLessThanEqualAndContractEndDateGreaterThanEqual(
            LocalDate date1,
            LocalDate date2
    );


    // ---------------------------------
    // ROUTE-BASED QUERIES
    // ---------------------------------

    // Find all bookings for a specific route
    List<Booking> findByRoute(Route route);

    // Count active bookings for a route (capacity checks)
    long countByRouteAndStatus(Route route, Booking.BookingStatus status);


    // ---------------------------------
    // PAYMENT & RATING SUPPORT
    // ---------------------------------

    // Ensure only one booking exists for the same parent & route in a period
    boolean existsByParentAndRouteAndContractStartDateLessThanEqualAndContractEndDateGreaterThanEqual(
            Parent parent,
            Route route,
            LocalDate endDate,
            LocalDate startDate
    );
}
