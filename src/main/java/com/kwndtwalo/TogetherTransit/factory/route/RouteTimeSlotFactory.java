package com.kwndtwalo.TogetherTransit.factory.route;

import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.route.RouteTimeSlot;
import com.kwndtwalo.TogetherTransit.util.Helper;

import java.time.LocalTime;

public class RouteTimeSlotFactory {

    public static RouteTimeSlot createRouteTimeSlot(
            RouteTimeSlot.TimeType timeType,
            LocalTime startTime,
            LocalTime endTime,
            Route route
    ) {

        if (!Helper.isValidTimeType(timeType)
                || !Helper.isValidTimeRange(startTime, endTime)
                || !Helper.isValidTimeTypeRange(timeType, startTime, endTime)) {
            return null;
        }

        return new RouteTimeSlot.Builder()
                .setTimeType(timeType)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setRoute(route)
                .build();
    }
}
