package com.kwndtwalo.TogetherTransit.repository.rating;

import com.kwndtwalo.TogetherTransit.domain.rating.Rating;
import com.kwndtwalo.TogetherTransit.domain.rating.Rating.RatingStatus;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Long> {

    // -------------------------------
    // DUPLICATE PREVENTION
    // -------------------------------

    // A booking can only be rated once
    Optional<Rating> findByBooking(Booking booking);

    boolean existsByBooking(Booking booking);


    // -------------------------------
    // DRIVER-FOCUSED QUERIES
    // -------------------------------

    // All ratings for a driver
    List<Rating> findByDriver(Driver driver);

    // Only approved ratings (what parents see)
    List<Rating> findByDriverAndStatus(Driver driver, RatingStatus status);

    // Driver ratings within a date range
    List<Rating> findByDriverAndRatedAtBetween(
            Driver driver,
            LocalDateTime start,
            LocalDateTime end
    );


    // -------------------------------
    // PARENT-FOCUSED QUERIES
    // -------------------------------

    // All ratings submitted by a parent
    List<Rating> findByParent(Parent parent);

    // Parent rating history with status
    List<Rating> findByParentAndStatus(Parent parent, RatingStatus status);


    // -------------------------------
    // MODERATION / ADMIN
    // -------------------------------

    // Ratings waiting for admin review
    List<Rating> findByStatus(RatingStatus status);

    // Recently submitted ratings (admin dashboard)
    List<Rating> findByRatedAtAfter(LocalDateTime dateTime);


}
