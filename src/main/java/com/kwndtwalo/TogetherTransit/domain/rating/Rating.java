package com.kwndtwalo.TogetherTransit.domain.rating;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;
    private String feedBackText;
    private LocalDateTime ratedAt;

    //Rating categories
    private int punctuality;
    private int safety;
    private int communication;

    //Who gave the rating.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", nullable = false)
    private Parent parent;

    //Who is rated
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driverId", nullable = false)
    private Driver driver;

    // Context: which booking this rating is for
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookingId", unique = true)
    private Booking booking;

    // Moderation & lifecycle
    @Enumerated(EnumType.STRING)
    private RatingStatus status;

    public enum RatingStatus {
        PENDING_REVIEW,
        APPROVED,
        REJECTED
    }

    protected Rating() {}

    private Rating(Builder builder) {
        ratingId = builder.ratingId;
        feedBackText = builder.feedBackText;
        ratedAt = builder.ratedAt;
        punctuality = builder.punctuality;
        safety = builder.safety;
        communication = builder.communication;
        parent = builder.parent;
        driver = builder.driver;
        booking = builder.booking;
        status = builder.status;
    }

    public Long getRatingId() {
        return ratingId;
    }

    public String getFeedBackText() {
        return feedBackText;
    }

    public LocalDateTime getRatedAt() {
        return ratedAt;
    }

    public int getPunctuality() {
        return punctuality;
    }

    public int getSafety() {
        return safety;
    }

    public int getCommunication() {
        return communication;
    }

    @Transient //tells JPA: this is NOT a column.
    //You must call this and display it in the screen.
    public double getOverallRating() {
        return (punctuality + safety + communication) / 3.0;
    }


    public Parent getParent() {
        return parent;
    }

    public Driver getDriver() {
        return driver;
    }

    public Booking getBooking() {
        return booking;
    }

    public RatingStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingId=" + getRatingId() +
                ", feedBackText='" + getFeedBackText() + '\'' +
                ", ratedAt=" + getRatedAt() +
                ", punctuality=" + getPunctuality() +
                ", safety=" + getSafety() +
                ", communication=" + getCommunication() +
                ", Overall Rating=" + getOverallRating() +
                ", parent=" + getParent() +
                ", driver=" + getDriver() +
                ", booking=" + getBooking() +
                ", status=" + getStatus() +
                '}';
    }

    public static class Builder {
        private Long ratingId;
        private String feedBackText;
        private LocalDateTime ratedAt;
        private int punctuality;
        private int safety;
        private int communication;
        private Parent parent;
        private Driver driver;
        private Booking booking;
        private RatingStatus status;

        public Builder setRatingId(Long ratingId) {
            this.ratingId = ratingId;
            return this;
        }
        public Builder setFeedBackText(String feedBackText) {
            this.feedBackText = feedBackText;
            return this;
        }
        public Builder setRatedAt(LocalDateTime ratedAt) {
            this.ratedAt = ratedAt;
            return this;
        }
        public Builder setPunctuality(int punctuality) {
            this.punctuality = punctuality;
            return this;
        }
        public Builder setSafety(int safety) {
            this.safety = safety;
            return this;
        }
        public Builder setCommunication(int communication) {
            this.communication = communication;
            return this;
        }
        public Builder setParent(Parent parent) {
            this.parent = parent;
            return this;
        }
        public Builder setDriver(Driver driver) {
            this.driver = driver;
            return this;
        }
        public Builder setBooking(Booking booking) {
            this.booking = booking;
            return this;
        }
        public Builder setStatus(RatingStatus status) {
            this.status = status;
            return this;
        }

        public Builder copy(Rating rating) {
            this.ratingId = rating.getRatingId();
            this.feedBackText = rating.getFeedBackText();
            this.ratedAt = rating.getRatedAt();
            this.punctuality = rating.getPunctuality();
            this.safety = rating.getSafety();
            this.communication = rating.getCommunication();
            this.parent = rating.getParent();
            this.driver = rating.getDriver();
            this.booking = rating.getBooking();
            this.status = rating.getStatus();
            return this;
        }

        public Rating build() {return new Rating(this);}
    }
}



