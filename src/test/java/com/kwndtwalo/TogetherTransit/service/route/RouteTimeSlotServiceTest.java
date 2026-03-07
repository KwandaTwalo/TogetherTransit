package com.kwndtwalo.TogetherTransit.service.route;

import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.route.RouteTimeSlot;
import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.dto.route.RouteTimeSlotDTO;
import com.kwndtwalo.TogetherTransit.factory.auth.AuthenticationFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.AddressFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;
import com.kwndtwalo.TogetherTransit.factory.route.RouteFactory;
import com.kwndtwalo.TogetherTransit.factory.route.RouteTimeSlotFactory;
import com.kwndtwalo.TogetherTransit.factory.school.SchoolFactory;
import com.kwndtwalo.TogetherTransit.factory.users.DriverFactory;
import com.kwndtwalo.TogetherTransit.service.school.SchoolService;
import com.kwndtwalo.TogetherTransit.service.users.DriverService;
import com.kwndtwalo.TogetherTransit.service.users.RoleService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class RouteTimeSlotServiceTest {

    @Autowired
    private RouteTimeSlotService timeSlotService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private RoleService roleService;

    private static RouteTimeSlot savedSlot;

    @Test
    void a_create() {

        // ---- DRIVER ----
        Role role = roleService.getByRoleName(Role.RoleName.DRIVER);

        Driver driver = DriverFactory.createDriver(
                "12345678910",
                2,
                "Sandile",
                "Sibiya",
                LocalDate.now(),
                User.AccountStatus.ACTIVE,
                ContactFactory.createContact("0712345678", "0798765432"),
                AddressFactory.createAddress("12", "Main Road", "Claremont", "Cape Town", 7708),
                AuthenticationFactory.createAuthentication(
                        "driver@gmail.com",
                        "Password123!",
                        LocalDateTime.now(),
                        false
                ),
                role
        );
        Driver savedDriver = driverService.create(driver);

        // ---- SCHOOL ----
        School savedSchool = schoolService.create(
                SchoolFactory.createSchool("Kwa-Komani Comprehensive School")
        );

        // ---- ROUTE ----
        Route route = RouteFactory.createRoute(
                "Home Section A",
                "Kwa-Komani School",
                true,
                1200.00,
                -33.987,
                18.432,
                -33.978,
                18.456,
                12.5,
                35,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
                Route.RouteStatus.ACTIVE,
                savedDriver,
                savedSchool,
                "Morning only"
        );

        Route savedRoute = routeService.create(route);

        // ---- TIME SLOT ----
        RouteTimeSlot slot = RouteTimeSlotFactory.createRouteTimeSlot(
                RouteTimeSlot.TimeType.MORNING,
                LocalTime.of(6, 30),
                LocalTime.of(7, 30),
                savedRoute
        );

        savedSlot = timeSlotService.create(slot);

        assertNotNull(savedSlot);
        assertNotNull(savedSlot.getSlotId());

        System.out.println("Created: " + new RouteTimeSlotDTO(savedSlot));
    }

    @Test
    void b_read() {
        RouteTimeSlot found = timeSlotService.read(savedSlot.getSlotId());
        assertNotNull(found);
        System.out.println("Read: " + new RouteTimeSlotDTO(found));
    }

    @Test
    void c_update() {
        RouteTimeSlot updated = new RouteTimeSlot.Builder()
                .copy(savedSlot)
                .setEndTime(LocalTime.of(7, 45))
                .build();

        RouteTimeSlot result = timeSlotService.update(updated);
        assertNotNull(result);

        System.out.println("Updated: " + new RouteTimeSlotDTO(result));
    }

    @Test
    void d_getAll() {
        List<RouteTimeSlot> slots = timeSlotService.getAllRouteTimeSlots();
        assertFalse(slots.isEmpty());

        slots.forEach(s -> System.out.println(new RouteTimeSlotDTO(s)));
    }

    @Test
    void e_delete() {
        boolean deleted = timeSlotService.delete(savedSlot.getSlotId());
        assertTrue(deleted);
        System.out.println("Deleted slot ID: " + savedSlot.getSlotId());
    }
}