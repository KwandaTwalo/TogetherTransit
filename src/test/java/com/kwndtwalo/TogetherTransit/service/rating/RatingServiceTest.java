package com.kwndtwalo.TogetherTransit.service.rating;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.rating.Rating;
import com.kwndtwalo.TogetherTransit.domain.rating.Rating.RatingStatus;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.factory.rating.RatingFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class RatingServiceTest {

    @Autowired
    private RatingService ratingService;

    // Persisted rating reference (used across tests)
    private static Rating savedRating;

    // Dummy related entities (normally created via their own services)
    private static Parent parent = new Parent.Builder().build();
    private static Driver driver = new Driver.Builder().build();
    private static Booking booking = new Booking.Builder().build();

    @Test
    void a_create() {
        Rating rating = RatingFactory.createRating(
                "Excellent service",
                LocalDateTime.now(),
                5,
                5,
                4,
                RatingStatus.PENDING_REVIEW,
                parent,
                driver,
                booking
        );

        assertNotNull(rating, "Factory should create a valid Rating");

        savedRating = ratingService.create(rating);

        assertNotNull(savedRating);
        assertNotNull(savedRating.getRatingId());

        System.out.println("Rating created: " + savedRating);
    }

    @Test
    void b_read() {
        Rating found = ratingService.read(savedRating.getRatingId());

        assertNotNull(found);
        assertEquals(savedRating.getRatingId(), found.getRatingId());

        System.out.println("Rating read successfully: " + found);
    }

    @Test
    void c_update() {
        Rating updated = new Rating.Builder()
                .copy(savedRating)
                .setFeedBackText("Updated feedback after review")
                .setStatus(RatingStatus.APPROVED)
                .build();

        Rating result = ratingService.update(updated);

        assertNotNull(result);
        assertEquals(RatingStatus.APPROVED, result.getStatus());

        savedRating = result;

        System.out.println("Rating updated: " + result);
    }

    @Test
    void d_getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();

        assertFalse(ratings.isEmpty());

        System.out.println("Total ratings in system: " + ratings.size());
    }

    @Test
    void e_getRatingByDriver() {
        List<Rating> ratings = ratingService.getRatingByDriver(driver);

        assertNotNull(ratings);

        System.out.println(" Ratings for driver: " + ratings.size());
    }

    @Test
    void f_getApprovedRatingsForDriver() {
        List<Rating> ratings =
                ratingService.getApprovedRatingsForDriver(driver);

        System.out.println("Approved ratings for driver: " + ratings.size());
    }

    @Test
    void g_getDriverRatingsBetweenDates() {
        List<Rating> ratings = ratingService.getDriverRatingsBetweenDates(
                driver,
                LocalDateTime.now().minusDays(7),
                LocalDateTime.now().plusDays(1)
        );

        System.out.println(" Ratings for driver in date range: " + ratings.size());
    }

    @Test
    void h_getRatingsByParent() {
        List<Rating> ratings = ratingService.getRatingsByParent(parent);

        System.out.println("Ratings submitted by parent: " + ratings.size());
    }

    @Test
    void i_getRatingsByParentAndStatus() {
        List<Rating> ratings = ratingService.getRatingsByParentAndStatus(
                parent,
                RatingStatus.APPROVED
        );

        System.out.println("Approved ratings by parent: " + ratings.size());
    }

    @Test
    void j_getPendingRatings() {
        List<Rating> pending = ratingService.getPendingRatings();

        System.out.println("Pending ratings for moderation: " + pending.size());
    }

    @Test
    void k_getRecentRatings() {
        List<Rating> recent = ratingService.getRecentRatings(
                LocalDateTime.now().minusDays(1)
        );

        System.out.println("Recent ratings submitted: " + recent.size());
    }

    @Test
    void l_bookingAlreadyRated() {
        boolean exists = ratingService.bookingAlreadyRated(booking);

        assertTrue(exists);

        System.out.println("Booking already rated? " + exists);
    }

    @Test
    void m_delete() {
        boolean deleted = ratingService.delete(savedRating.getRatingId());

        assertTrue(deleted);

        System.out.println("Rating deleted successfully");
    }
}
