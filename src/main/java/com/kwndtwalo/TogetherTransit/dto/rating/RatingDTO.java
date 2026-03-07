package com.kwndtwalo.TogetherTransit.dto.rating;

import com.kwndtwalo.TogetherTransit.domain.rating.Rating;

import java.time.LocalDateTime;

public class RatingDTO {

    private Long ratingId;
    private String feedBackText;
    private LocalDateTime ratedAt;

    private int punctuality;
    private int safety;
    private int communication;
    private double overallRating;

    private String status;

    // Relations (IDs only)
    private Long parentId;
    private Long driverId;
    private Long bookingId;

    public RatingDTO(Rating rating) {
        this.ratingId = rating.getRatingId();
        this.feedBackText = rating.getFeedBackText();
        this.ratedAt = rating.getRatedAt();

        this.punctuality = rating.getPunctuality();
        this.safety = rating.getSafety();
        this.communication = rating.getCommunication();
        this.overallRating = rating.getOverallRating();

        this.status = rating.getStatus().name();

        // Lazy-safe relations
        this.parentId = rating.getParent() != null
                ? rating.getParent().getUserId()
                : null;

        this.driverId = rating.getDriver() != null
                ? rating.getDriver().getUserId()
                : null;

        this.bookingId = rating.getBooking() != null
                ? rating.getBooking().getBookingId()
                : null;
    }

    @Override
    public String toString() {
        return "\nRatingDTO{" +
                "ratingId=" + ratingId +
                ", feedBackText='" + feedBackText + '\'' +
                ", ratedAt=" + ratedAt +
                ", punctuality=" + punctuality +
                ", safety=" + safety +
                ", communication=" + communication +
                ", overallRating=" + overallRating +
                ", status='" + status + '\'' +
                ", parentId=" + parentId +
                ", driverId=" + driverId +
                ", bookingId=" + bookingId +
                '}';
    }
}