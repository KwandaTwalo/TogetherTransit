package com.kwndtwalo.TogetherTransit.factory.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.booking.ChildBooking;
import com.kwndtwalo.TogetherTransit.domain.child.Child;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ChildBookingFactoryTest {

    private Booking mockBooking = mock(Booking.class);
    private Child mockChild = mock(Child.class);
    private Route mockRoute = mock(Route.class);

    @Test
    void createChildBooking() {

        ChildBooking childBooking = ChildBookingFactory.createChildBooking(
                true,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
                ChildBooking.ChildBookingStatus.ACTIVE,
                mockBooking,
                mockChild,
                mockRoute
        );

        assertNotNull(childBooking);
        assertTrue(childBooking.getUsesCustomSchedule());

        System.out.println("ChildBooking created successfully with a custom schedule.\n" + childBooking);
    }

    @Test
    void createChildBooking_withoutCustomSchedule_success() {

        ChildBooking childBooking = ChildBookingFactory.createChildBooking(
                false,
                Set.of(),
                ChildBooking.ChildBookingStatus.ACTIVE,
                mockBooking,
                mockChild,
                null
        );

        assertNotNull(childBooking);
        assertFalse(childBooking.getUsesCustomSchedule());

        System.out.println("ChildBooking created successfully using the default booking schedule.\n" + childBooking);
    }

    @Test
    void createChildBooking_customScheduleWithoutDays_fails() {

        ChildBooking childBooking = ChildBookingFactory.createChildBooking(
                true,
                Set.of(),
                ChildBooking.ChildBookingStatus.ACTIVE,
                mockBooking,
                mockChild,
                mockRoute
        );

        assertNull(childBooking);
        System.out.println("ChildBooking creation failed due to missing custom schedule days.\n" + childBooking);
    }
}