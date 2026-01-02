package com.kwndtwalo.TogetherTransit.factory.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.booking.ChildBooking;
import com.kwndtwalo.TogetherTransit.domain.child.Child;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.util.Helper;

import java.time.DayOfWeek;
import java.util.Set;

public class ChildBookingFactory {

    public static ChildBooking createChildBooking(boolean usesCustomSchedule,
                                                  Set<DayOfWeek> customDays,
                                                  ChildBooking.ChildBookingStatus status,
                                                  Booking booking,
                                                  Child child,
                                                  Route route) {

        if (!Helper.isValidCustomSchedule(usesCustomSchedule, customDays)) {
            return null;
        }

        return new ChildBooking.Builder()
                .setUsesCustomSchedule(usesCustomSchedule)
                .setCustomDays(customDays)
                .setChildBookingStatus(status)
                .setBooking(booking)
                .setChild(child)
                .setRoute(route)
                .build();
    }
}
