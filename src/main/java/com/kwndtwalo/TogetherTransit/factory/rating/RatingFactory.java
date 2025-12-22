package com.kwndtwalo.TogetherTransit.factory.rating;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.rating.Rating;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.util.Helper;

import java.time.LocalDateTime;

public class RatingFactory {

    public static Rating createRating(String feedback, LocalDateTime ratedAt,
                                      int punctuality, int safety, int communication,
                                      Rating.RatingStatus ratingStatus,
                                      Parent parent, Driver driver, Booking booking) {

        if (!Helper.isValidFeedback(feedback)) { return null; }
        if (!Helper.isValidScore(punctuality) || !Helper.isValidScore(safety) || !Helper.isValidScore(communication)) {
            return null;
        }

        return new Rating.Builder()
                .setFeedBackText(feedback)
                .setRatedAt(ratedAt)
                .setPunctuality(punctuality)
                .setSafety(safety)
                .setCommunication(communication)
                .setStatus(ratingStatus)
                .setParent(parent)
                .setDriver(driver)
                .setBooking(booking)
                .build();
    }
}
