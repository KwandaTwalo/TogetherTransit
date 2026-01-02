package com.kwndtwalo.TogetherTransit.factory.route;

import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.route.RouteTimeSlot;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RouteTimeSlotFactoryTest {

    private Route mockRoute = Mockito.mock(Route.class);

    @Test
    void createRouteTimeSlot() {

        RouteTimeSlot slot = RouteTimeSlotFactory.createRouteTimeSlot(
                RouteTimeSlot.TimeType.MORNING,
                LocalTime.of(6, 30),
                LocalTime.of(7, 30),
                mockRoute
        );

        assertNotNull(slot);
        assertEquals(RouteTimeSlot.TimeType.MORNING, slot.getTimeType());

        System.out.println("Morning RouteTimeSlot created successfully:\n " + slot);
    }

    @Test
    void createRouteTimeSlot_fail_invalidTimeRange() {

        RouteTimeSlot slot = RouteTimeSlotFactory.createRouteTimeSlot(
                RouteTimeSlot.TimeType.MORNING,
                LocalTime.of(8, 0),
                LocalTime.of(7, 0),
                mockRoute
        );

        assertNull(slot);
        System.out.println("Invalid Time Range: \n " + slot);
    }

    @Test
    void createRouteTimeSlot_fail_outsideMorningRange() {

        RouteTimeSlot slot = RouteTimeSlotFactory.createRouteTimeSlot(
                RouteTimeSlot.TimeType.MORNING,
                LocalTime.of(13, 0),
                LocalTime.of(14, 0),
                mockRoute
        );

        assertNull(slot);
        System.out.println("The time range is invalid does not represent Time type: \n " + slot);
    }
}