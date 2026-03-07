package com.kwndtwalo.TogetherTransit.dto.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;

import java.time.LocalDate;

public class BookingDTO {

    private Long bookingId;
    private LocalDate bookingDate;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private Booking.BookingStatus status;
    private double totalAmount;

    private Long parentId;
    private Long routeId;

    public BookingDTO(Booking booking) {
        this.bookingId = booking.getBookingId();
        this.bookingDate = booking.getBookingDate();
        this.contractStartDate = booking.getContractStartDate();
        this.contractEndDate = booking.getContractEndDate();
        this.status = booking.getStatus();
        this.totalAmount = booking.getTotalAmount();

        // LAZY SAFE: only IDs
        this.parentId = booking.getParent() != null
                ? booking.getParent().getUserId()
                : null;

        this.routeId = booking.getRoute() != null
                ? booking.getRoute().getRouteId()
                : null;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingId=" + bookingId +
                ", bookingDate=" + bookingDate +
                ", contractStartDate=" + contractStartDate +
                ", contractEndDate=" + contractEndDate +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", parentId=" + parentId +
                ", routeId=" + routeId +
                '}';
    }
}
