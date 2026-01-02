package com.kwndtwalo.TogetherTransit.factory.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.util.Helper;

import java.time.LocalDate;

public class BookingFactory {

    public static Booking createBooking(LocalDate bookingDate,
                                        LocalDate contractStartDate,
                                        LocalDate contractEndDate,
                                        Booking.BookingStatus status,
                                        double totalAmount,
                                        Parent parent,
                                        Route route) {

        if (!Helper.isValidBookingDate(bookingDate)
                || !Helper.isValidContractPeriod(contractStartDate, contractEndDate)
                || !Helper.isValidPaymentAmount(totalAmount)
                ) {
            return null;
        }

        return new Booking.Builder()
                .setBookingDate(bookingDate)
                .setContractStartDate(contractStartDate)
                .setContractEndDate(contractEndDate)
                .setStatus(status)
                .setTotalAmount(totalAmount)
                .setParent(parent)
                .setRoute(route)
                .build();
    }
}
