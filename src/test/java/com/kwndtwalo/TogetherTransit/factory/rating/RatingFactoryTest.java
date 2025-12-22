package com.kwndtwalo.TogetherTransit.factory.rating;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.rating.Rating;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RatingFactoryTest {

    Parent mockParent = mock(Parent.class);
    Driver mockDriver = mock(Driver.class);
    Booking mockBooking = mock(Booking.class);

    private Rating rating1 = RatingFactory.createRating("I have never had problems with the driver he is" +
            " punctual and my child is also happy", LocalDateTime.now(), 5, 4, 5,
            Rating.RatingStatus.APPROVED, mockParent, mockDriver, mockBooking);

    @Test
    void createRating() {
        assertNotNull(rating1);
        System.out.println("Rating created successfully: " + rating1);
    }

    @Test
    void testInvalidFeedback() {
        Rating rating2 = RatingFactory.createRating("ok", LocalDateTime.now(), 5, 4, 5,
                Rating.RatingStatus.APPROVED, mockParent, mockDriver, mockBooking);
        assertNull(rating2);
        System.out.println("The feedback must be at least 5 to 500 characters");
    }

    @Test
    void testInvalidScore() {
        Rating rating3 = RatingFactory.createRating("ok", LocalDateTime.now(), 7, 4, 5,
                Rating.RatingStatus.APPROVED, mockParent, mockDriver, mockBooking);
        assertNull(rating3);
        System.out.println("The score must be between 1 and 5");
    }
}