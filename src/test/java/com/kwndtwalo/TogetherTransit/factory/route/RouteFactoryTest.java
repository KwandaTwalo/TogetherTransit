package com.kwndtwalo.TogetherTransit.factory.route;

import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.DayOfWeek;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RouteFactoryTest {

    private Driver mockDriver = Mockito.mock(Driver.class);
    private School mockSchool = Mockito.mock(School.class);

    @Test
    void createRoute() {
        Route route = RouteFactory.createRoute(
                "Summer Greens",
                "Milnerton High School",
                true,
                1200.00,
                -33.918861,
                18.423300,
                -33.925000,
                18.430000,
                12.5,
                35,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
                Route.RouteStatus.ACTIVE,
                mockDriver,
                mockSchool,
                "Pickup at main gate"
        );

        assertNotNull(route);
        assertEquals("Summer Greens", route.getPickupPoint());
        assertEquals(1200.00, route.getMonthlyFee());
        assertEquals(Route.RouteStatus.ACTIVE, route.getStatus());

        System.out.println("Route created successfully: " + route);

    }

    @Test
    void createRoute_invalidCoordinates_returnsNull() {

        Route route = RouteFactory.createRoute(
                "Home",
                "School",
                true,
                1000,
                -200.0, // invalid latitude
                18.4,
                -33.9,
                18.4,
                10,
                30,
                Set.of(DayOfWeek.MONDAY),
                Route.RouteStatus.ACTIVE,
                mockDriver,
                mockSchool,
                null
        );

        assertNull(route);
        System.out.println("Route creation failed due to invalid GPS coordinates\n" + route);
    }

    @Test
    void createRoute_nonMonthlyWithFee_returnsNull() {

        Route route = RouteFactory.createRoute(
                "Point A",
                "Point B",
                false,
                500, // fee not allowed
                -33.9,
                18.4,
                -33.9,
                18.4,
                5,
                15,
                Set.of(DayOfWeek.FRIDAY),
                Route.RouteStatus.SCHEDULED,
                mockDriver,
                mockSchool,
                null
        );

        assertNull(route);
        System.out.println("Route creation failed due to invalid monthly fee logic\n" + route);
    }
}