package com.kwndtwalo.TogetherTransit.domain.booking;

import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    // Monthly subscription
    private LocalDate bookingDate;        // When booking was made
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;

    // The parent who creates the booking
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Parent parent;

    // The route being booked (includes driver, timeslots, school)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routeId")
    private Route route;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChildBooking> childBookings = new HashSet<>();


    @Enumerated(EnumType.STRING)
    private BookingStatus status;         // ACTIVE, CANCELLED, PENDING, COMPLETED

    private double totalAmount;           // Calculated from route.monthlyFee

    public enum BookingStatus {
        PENDING,
        ACTIVE,
        COMPLETED,
        CANCELLED
    }

    protected Booking() {}

    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.bookingDate = builder.bookingDate;
        this.contractStartDate = builder.contractStartDate;
        this.contractEndDate = builder.contractEndDate;
        this.parent = builder.parent;
        this.route = builder.route;
        this.status = builder.status;
        this.totalAmount = builder.totalAmount;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public LocalDate getContractStartDate() {
        return contractStartDate;
    }

    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    public Parent getParent() {
        return parent;
    }

    public Route getRoute() {
        return route;
    }

    public Set<ChildBooking> getChildBookings() {
        return childBookings;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + getBookingId() +
                ", bookingDate=" + getBookingDate() +
                ", contractStartDate=" + getContractStartDate() +
                ", contractEndDate=" + getContractEndDate() +
                ", parent=" + getParent() +
                ", route=" + getRoute() +
                ", childBookings=" + getChildBookings() +
                ", status=" + getStatus() +
                ", totalAmount=" + getTotalAmount() +
                '}';
    }

    public static class Builder {
        private Long bookingId;
        private LocalDate bookingDate;
        private LocalDate contractStartDate;
        private LocalDate contractEndDate;
        private Parent parent;
        private Route route;
        private Set<ChildBooking> childBookings = new HashSet<>();
        private BookingStatus status;
        private double totalAmount;

        public Builder setBookingId(Long bookingId) {
            this.bookingId = bookingId;
            return this;
        }
        public Builder setBookingDate(LocalDate bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }
        public Builder setContractStartDate(LocalDate contractStartDate) {
            this.contractStartDate = contractStartDate;
            return this;
        }
        public Builder setContractEndDate(LocalDate contractEndDate) {
            this.contractEndDate = contractEndDate;
            return this;
        }
        public Builder setParent(Parent parent) {
            this.parent = parent;
            return this;
        }
        public Builder setRoute(Route route) {
            this.route = route;
            return this;
        }
        public Builder setChildBookings(Set<ChildBooking> childBookings) {
            this.childBookings = childBookings;
            return this;
        }
        public Builder setStatus(BookingStatus status) {
            this.status = status;
            return this;
        }
        public Builder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder copy(Booking booking) {
            this.bookingId = booking.getBookingId();
            this.bookingDate = booking.getBookingDate();
            this.contractStartDate = booking.getContractStartDate();
            this.contractEndDate = booking.getContractEndDate();
            this.parent = booking.getParent();
            this.route = booking.getRoute();
            this.childBookings = booking.getChildBookings();
            this.status = booking.getStatus();
            this.totalAmount = booking.getTotalAmount();
            return this;
        }

        public Booking build() {return new Booking(this);}
    }
}
