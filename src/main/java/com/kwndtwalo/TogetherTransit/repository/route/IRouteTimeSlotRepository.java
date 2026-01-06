package com.kwndtwalo.TogetherTransit.repository.route;

import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.route.RouteTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IRouteTimeSlotRepository extends JpaRepository<RouteTimeSlot, Long> {

    // ---------------------------------
    // ROUTE-BASED QUERIES
    // ---------------------------------

    // Fetch all time slots for a route
    List<RouteTimeSlot> findByRoute(Route route);

    // Fetch time slots for a route ordered by start time
    List<RouteTimeSlot> findByRouteOrderByStartTimeAsc(Route route);


    // ---------------------------------
    // TIME TYPE QUERIES
    // ---------------------------------

    // Fetch all MORNING / AFTERNOON / EVENING slots
    List<RouteTimeSlot> findByTimeType(RouteTimeSlot.TimeType timeType);

    // Fetch time slots for a route by time type
    List<RouteTimeSlot> findByRouteAndTimeType(
            Route route,
            RouteTimeSlot.TimeType timeType
    );


    // ---------------------------------
    // SCHEDULING VALIDATION
    // ---------------------------------

    // Detect overlapping time slots on the same route
    List<RouteTimeSlot> findByRouteAndStartTimeLessThanAndEndTimeGreaterThan(
            Route route,
            LocalTime endTime,
            LocalTime startTime
    );


    // ---------------------------------
    // DUPLICATE PREVENTION
    // ---------------------------------

    // Prevent duplicate identical time slots
    Optional<RouteTimeSlot> findByRouteAndStartTimeAndEndTime(
            Route route,
            LocalTime startTime,
            LocalTime endTime
    );
}
