package com.kwndtwalo.TogetherTransit.service.route;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.dto.route.RouteDTO;
import com.kwndtwalo.TogetherTransit.factory.auth.AuthenticationFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.AddressFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;
import com.kwndtwalo.TogetherTransit.factory.route.RouteFactory;
import com.kwndtwalo.TogetherTransit.factory.school.SchoolFactory;
import com.kwndtwalo.TogetherTransit.factory.users.DriverFactory;
import com.kwndtwalo.TogetherTransit.service.school.SchoolService;
import com.kwndtwalo.TogetherTransit.service.users.DriverService;
import com.kwndtwalo.TogetherTransit.service.users.RoleService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class RouteServiceTest {

    @Autowired
    private RouteService routeService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private DriverService driverService;

    @Autowired
    RoleService roleService;

    private static Route savedRoute;


    @Test
    void a_create() {

        Contact contact = ContactFactory.createContact(
                "0712345678",
                "0798765432"
        );

        Address address = AddressFactory.createAddress(
                "12",
                "Main Road",
                "Claremont",
                "Cape Town",
                7708
        );

        Authentication authentication = AuthenticationFactory.createAuthentication(
                "sandile@gmail.com",
                "San@1234",
                LocalDateTime.now(),
                false
        );

        Role role = roleService.getByRoleName(Role.RoleName.DRIVER);

        Driver driver = DriverFactory.createDriver(
                "12345678910",
                2,
                "Sandile",
                "Sibiya",
                LocalDate.now(),
                User.AccountStatus.ACTIVE,
                contact,
                address,
                authentication,
                role
        );
        Driver savedDriver = driverService.create(driver);

        School school = SchoolFactory.createSchool("Kwa-Komani Comprehensive School");
        School savedSchool = schoolService.create(school);

        Route route = RouteFactory.createRoute(
                "Home - Section A",
                "Kwa-Komani Comprehensive School",
                true,
                1200.00,
                -33.987,
                18.432,
                -33.978,
                18.456,
                12.5,
                35,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
                Route.RouteStatus.SCHEDULED,
                savedDriver,
                savedSchool,
                "Morning pickup only"
        );

        savedRoute = routeService.create(route);

        assertNotNull(savedRoute);
        assertNotNull(savedRoute.getRouteId());
        System.out.println("created route successfully: " + savedRoute);
        //System.out.println("Route created: " + new RouteDTO(savedRoute));
    }

    @Test
    void b_read() {
        Route found = routeService.read(savedRoute.getRouteId());
        assertNotNull(found);
        assertEquals(savedRoute.getRouteId(), found.getRouteId());
        //System.out.println("read route successfully: " + found);
        System.out.println("read route successfully: " + new RouteDTO(found));//using the DTO class to avoid the error of lazy....no session.
    }

    @Test
    void c_update() {
        Route updated = new Route.Builder()
                .copy(savedRoute)
                .setMonthlyFee(1350.00)
                .build();

        Route result = routeService.update(updated);
        assertNotNull(result);
        assertEquals(1350.00, result.getMonthlyFee());
        System.out.println("update route successfully: " + result);
    }

    @Test
    void d_getAllRoutes() {
        List<Route> routes = routeService.getAllRoutes();
        assertFalse(routes.isEmpty());
        //System.out.println("getAllRoutes successfully: " + routes);
        routes.forEach(r -> System.out.println(new RouteDTO(r)));//using the DTO class to avoid the error of lazy....no session.

    }

    @Test
    void e_delete() {
        boolean result = routeService.delete(savedRoute.getRouteId());
        assertTrue(result);
        System.out.println("delete route successfully: " + savedRoute);
    }
}