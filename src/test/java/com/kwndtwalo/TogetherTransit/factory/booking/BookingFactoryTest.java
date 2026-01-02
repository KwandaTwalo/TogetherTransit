package com.kwndtwalo.TogetherTransit.factory.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BookingFactoryTest {

    private Parent mockParent = mock(Parent.class);
    private Route mockRoute = mock(Route.class);

    @Test
    void createBooking() {

        Booking booking = BookingFactory.createBooking(
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                Booking.BookingStatus.ACTIVE,
                2500.00,
                mockParent,
                mockRoute
        );

        assertNotNull(booking);
        assertEquals(Booking.BookingStatus.ACTIVE, booking.getStatus());

        System.out.println("Booking created successfully with valid monthly contract details: \n" + booking);
    }

    @Test
    void createBooking_invalidContractPeriod_returnsNull() {

        Booking booking = BookingFactory.createBooking(
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                LocalDate.now(),
                Booking.BookingStatus.ACTIVE,
                2500.00,
                mockParent,
                mockRoute
        );

        assertNull(booking);
        System.out.println("Booking creation failed due to invalid contract period. \n" + booking);
    }

    @Test
    void createBooking_invalidAmount_returnsNull() {

        Booking booking = BookingFactory.createBooking(
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                Booking.BookingStatus.ACTIVE,
                0,
                mockParent,
                mockRoute
        );

        assertNull(booking);
        System.out.println("Booking creation failed due to invalid total amount. \n" + booking);
    }
}