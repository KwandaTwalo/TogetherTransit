package com.kwndtwalo.TogetherTransit.service.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.booking.ChildBooking;
import com.kwndtwalo.TogetherTransit.domain.child.Child;
import com.kwndtwalo.TogetherTransit.dto.booking.ChildBookingDTO;
import com.kwndtwalo.TogetherTransit.factory.booking.ChildBookingFactory;
import com.kwndtwalo.TogetherTransit.factory.child.ChildFactory;
import com.kwndtwalo.TogetherTransit.service.child.ChildService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ChildBookingServiceTest {

    @Autowired
    private ChildBookingService childBookingService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ChildService childService;

    private static ChildBooking savedChildBooking;

    @Test
    void a_createChildBooking() {

        Booking booking = bookingService.getAllBookings().get(0);

        Child child = ChildFactory.createChild(
                "Litha",
                "Twalo",
                LocalDate.of(2015, 5, 10),
                "grade 3",
                booking.getParent()
        );

        Child savedChild = childService.create(child);
        assertNotNull(savedChild);

        ChildBooking childBooking = ChildBookingFactory.createChildBooking(
                true,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                ChildBooking.ChildBookingStatus.ACTIVE,
                booking,
                savedChild,
                booking.getRoute()
        );

        savedChildBooking = childBookingService.create(childBooking);
        assertNotNull(savedChildBooking);

        System.out.println("ChildBooking created:");
        System.out.println(new ChildBookingDTO(savedChildBooking));
    }

    @Test
    void b_readChildBooking() {

        ChildBooking found = childBookingService.read(savedChildBooking.getChildBookingId());
        assertNotNull(found);

        System.out.println("ChildBooking read:");
        System.out.println(new ChildBookingDTO(found));
    }

    @Test
    void c_updateChildBooking() {

        ChildBooking updated = new ChildBooking.Builder()
                .copy(savedChildBooking)
                .setCustomDays(Set.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY))
                .build();

        ChildBooking result = childBookingService.update(updated);
        assertNotNull(result);

        System.out.println("ChildBooking updated:");
        System.out.println(new ChildBookingDTO(result));
    }

    @Test
    void d_getAllChildBookings() {
        assertFalse(childBookingService.getAllChildBookings().isEmpty());
        System.out.println("All child bookings fetched successfully");
    }

    @Test
    void e_cancelChildBooking() {

        boolean cancelled = childBookingService.delete(savedChildBooking.getChildBookingId());
        assertTrue(cancelled);

        System.out.println("ChildBooking cancelled successfully");
    }
}