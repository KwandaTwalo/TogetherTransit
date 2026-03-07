package com.kwndtwalo.TogetherTransit.service.rating;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.rating.Rating;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.repository.rating.IRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingService implements IRatingService {

    private final IRatingRepository ratingRepository;

    @Autowired
    public RatingService(IRatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**CRUD OPERATIONS*/

    /*
     * CREATE
     * Business rule:
     * - A booking can only be rated ONCE.
     * - If a rating already exists for the booking, return it instead of duplicating.
     * - New ratings usually start as PENDING_REVIEW.
     */
    @Override
    public Rating create(Rating rating) {

        if (rating == null || rating.getBooking() == null) {
            return null;
        }

        return ratingRepository
                .findByBooking(rating.getBooking())
                .orElseGet(() -> ratingRepository.save(rating));
    }

    @Override
    public Rating read(Long Id) {
        return ratingRepository.findById(Id).orElse(null);
    }

    @Override
    public Rating update(Rating rating) {

        if (rating == null || rating.getRatingId() == null) { return null; }

        if (!ratingRepository.existsById(rating.getRatingId())) {return null;}

        return ratingRepository.save(rating);
    }

    @Override
    public boolean delete(Long Id) {
        if (!ratingRepository.existsById(Id)) {
            return false;
        }

        ratingRepository.deleteById(Id);
        return true;
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    /**CUSTOM BUSINESS QUERIES*/


    /*
     * DRIVER VIEW
     * All ratings (approved + pending) for a driver.
     */
    public List<Rating> getRatingByDriver(Driver driver) {
        return ratingRepository.findByDriver(driver);
    }

    /*
     * PUBLIC VIEW
     * Only APPROVED ratings for a driver (what parents see).
     */
    public List<Rating> getApprovedRatingsForDriver(Driver driver) {
        return ratingRepository.findByDriverAndStatus(driver, Rating.RatingStatus.APPROVED);
    }

    /*
     * DRIVER ANALYTICS
     * Ratings for a driver within a date range.
     */
    public List<Rating> getDriverRatingsBetweenDates(
            Driver driver,
            LocalDateTime start,
            LocalDateTime end
    ) {
        return ratingRepository.findByDriverAndRatedAtBetween(driver, start, end);
    }

    /*
     * PARENT HISTORY
     * All ratings submitted by a parent.
     */
    public List<Rating> getRatingsByParent(Parent parent) {
        return ratingRepository.findByParent(parent);
    }

    /*
     * PARENT FILTER
     * Parent ratings filtered by status.
     */
    public List<Rating> getRatingsByParentAndStatus(
            Parent parent,
            Rating.RatingStatus status
    ) {
        return ratingRepository.findByParentAndStatus(parent, status);
    }

    /*
     * ADMIN DASHBOARD
     * Ratings waiting for moderation.
     */
    public List<Rating> getPendingRatings() {
        return ratingRepository.findByStatus(Rating.RatingStatus.PENDING_REVIEW);
    }

    /*
     * ADMIN DASHBOARD
     * Recently submitted ratings.
     */
    public List<Rating> getRecentRatings(LocalDateTime since) {
        return ratingRepository.findByRatedAtAfter(since);
    }

    /*
     * DUPLICATE CHECK
     * Used before allowing a parent to rate a booking.
     */
    public boolean bookingAlreadyRated(Booking booking) {
        return ratingRepository.existsByBooking(booking);
    }

}
