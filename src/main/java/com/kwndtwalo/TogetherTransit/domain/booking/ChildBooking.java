package com.kwndtwalo.TogetherTransit.domain.booking;

/*This class allows to customise a schedule for each child without changing the Booking.
* FOR EXAMPLE:
* Child 1 attends school Mon-Fri
* Child 3 attends Sat extra classes
* Child 3 attends evening study group during exams.
* Then we will use Booking class to book for all the children. meaning these 3 children belong to one parent although the system will create 3 childBooking rows. But belong to 1 Booking.
* Then use childBooking class to customise schedule per child to accommodate their schedules as mentioned above.*/


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
    private boolean usesCustomSchedule;

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

    @Enumerated(EnumType.STRING)
    private ChildBookingStatus childBookingStatus;

    public enum ChildBookingStatus {
        ACTIVE,
        PAUSED,
        CANCELLED,
    }

    protected ChildBooking() {}

    private ChildBooking(Builder builder) {
        this.childBookingId = builder.childBookingId;
        this.usesCustomSchedule = builder.usesCustomSchedule;
        this.booking = builder.booking;
        this.child = builder.child;
        this.route = builder.route;
        this.customDays = builder.customDays;
        this.childBookingStatus = builder.childBookingStatus;

    }

    public Long getChildBookingId() {
        return childBookingId;
    }

    public boolean getUsesCustomSchedule() {
        return usesCustomSchedule;
    }

    public Booking getBooking() {
        return booking;
    }

    public Child getChild() {
        return child;
    }

    public Route getRoute() {
        return route;
    }

    public Set<DayOfWeek> getCustomDays() {
        return customDays;
    }

    public ChildBookingStatus getChildBookingStatus() {
        return childBookingStatus;
    }

    @Override
    public String toString() {
        return "ChildBooking{" +
                "childBookingId=" + getChildBookingId() +
                ", usesCustomSchedule=" + getUsesCustomSchedule() +
                ", booking=" + getBooking() +
                ", child=" + getChild() +
                ", route=" + getRoute() +
                ", customDays=" + getCustomDays() +
                ", childBookingStatus=" + getChildBookingStatus() +
                '}';
    }

    public static class Builder {
        private Long childBookingId;
        private boolean usesCustomSchedule;
        private Booking booking;
        private Child child;
        private Route route;
        private Set<DayOfWeek> customDays = new HashSet<>();
        private ChildBookingStatus childBookingStatus;

        public Builder setChildBookingId(Long childBookingId) {
            this.childBookingId = childBookingId;
            return this;
        }
        public Builder setUsesCustomSchedule(boolean usesCustomSchedule) {
            this.usesCustomSchedule = usesCustomSchedule;
            return this;
        }
        public Builder setBooking(Booking booking) {
            this.booking = booking;
            return this;
        }
        public Builder setChild(Child child) {
            this.child = child;
            return this;
        }
        public Builder setRoute(Route route) {
            this.route = route;
            return this;
        }
        public Builder setCustomDays(Set<DayOfWeek> customDays) {
            this.customDays = customDays;
            return this;
        }
        public Builder setChildBookingStatus(ChildBookingStatus childBookingStatus) {
            this.childBookingStatus = childBookingStatus;
            return this;
        }

        public Builder copy(ChildBooking booking) {
            this.childBookingId = booking.getChildBookingId();
            this.usesCustomSchedule = booking.getUsesCustomSchedule();
            this.booking = booking.getBooking();
            this.child = booking.getChild();
            this.route = booking.getRoute();
            this.customDays = booking.getCustomDays();
            this.childBookingStatus = booking.getChildBookingStatus();
            return this;
        }

        public ChildBooking build() {return new ChildBooking(this);}

    }
}
