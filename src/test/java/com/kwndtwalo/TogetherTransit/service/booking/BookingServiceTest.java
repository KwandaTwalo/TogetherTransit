package com.kwndtwalo.TogetherTransit.service.booking;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.dto.booking.BookingDTO;
import com.kwndtwalo.TogetherTransit.factory.auth.AuthenticationFactory;
import com.kwndtwalo.TogetherTransit.factory.booking.BookingFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.AddressFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;
import com.kwndtwalo.TogetherTransit.factory.route.RouteFactory;
import com.kwndtwalo.TogetherTransit.factory.school.SchoolFactory;
import com.kwndtwalo.TogetherTransit.factory.users.DriverFactory;
import com.kwndtwalo.TogetherTransit.factory.users.ParentFactory;
import com.kwndtwalo.TogetherTransit.service.route.RouteService;
import com.kwndtwalo.TogetherTransit.service.school.SchoolService;
import com.kwndtwalo.TogetherTransit.service.users.DriverService;
import com.kwndtwalo.TogetherTransit.service.users.ParentService;
import com.kwndtwalo.TogetherTransit.service.users.RoleService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ParentService parentService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private RoleService roleService;

    private static Booking savedBooking;

    @Test
    void a_createBooking() {

        // ---------- ROLES ----------
        Role parentRole = roleService.getByRoleName(Role.RoleName.PARENT);
        Role driverRole = roleService.getByRoleName(Role.RoleName.DRIVER);

        // ---------- AUTHENTICATION ----------
        Authentication parentAuth = AuthenticationFactory.createAuthentication(
                "parent@gmail.com",
                "Parent@123",
                LocalDateTime.now(),
                false
        );

        Authentication driverAuth = AuthenticationFactory.createAuthentication(
                "driver@gmail.com",
                "Driver@123",
                LocalDateTime.now(),
                false
        );

        // ---------- PARENT ----------
        Parent parent = ParentFactory.createParent(
                "Aphiwe",
                "Twalo",
                LocalDate.now(),
                User.AccountStatus.ACTIVE,
                ContactFactory.createContact("0710000000", "0790000000"),
                AddressFactory.createAddress(
                        "18",
                        "Sir Lowry Rd",
                        "Foreshore",
                        "Cape Town",
                        8001
                ),
                parentAuth,
                parentRole
        );

        Parent savedParent = parentService.create(parent);
        assertNotNull(savedParent);

        // ---------- DRIVER ----------
        Driver driver = DriverFactory.createDriver(
                "12345678910",
                5,
                "Sandile",
                "Sibiya",
                LocalDate.now(),
                User.AccountStatus.ACTIVE,
                ContactFactory.createContact("0721111111", "0788888888"),
                AddressFactory.createAddress(
                        "22",
                        "Main Road",
                        "Milnerton",
                        "Cape Town",
                        7441
                ),
                driverAuth,
                driverRole
        );

        Driver savedDriver = driverService.create(driver);
        assertNotNull(savedDriver);

        // ---------- SCHOOL ----------
        School school = schoolService.create(
                SchoolFactory.createSchool("Milnerton High School")
        );
        assertNotNull(school);

        // ---------- ROUTE ----------
        Route route = routeService.create(
                RouteFactory.createRoute(
                        "Home",
                        "Milnerton High School",
                        true,
                        1200,
                        -33.9,
                        18.4,
                        -33.8,
                        18.5,
                        10,
                        30,
                        Set.of(
                                DayOfWeek.MONDAY,
                                DayOfWeek.TUESDAY,
                                DayOfWeek.WEDNESDAY,
                                DayOfWeek.THURSDAY,
                                DayOfWeek.FRIDAY
                        ),
                        Route.RouteStatus.ACTIVE,
                        savedDriver,
                        school,
                        "Standard weekday transport"
                )
        );

        assertNotNull(route);

        // ---------- BOOKING ----------
        Booking booking = BookingFactory.createBooking(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusMonths(1),
                Booking.BookingStatus.ACTIVE,
                1200,
                savedParent,
                route
        );

        savedBooking = bookingService.create(booking);

        assertNotNull(savedBooking);
        assertNotNull(savedBooking.getBookingId());

        System.out.println("Booking created successfully:");
        System.out.println(new BookingDTO(savedBooking));
    }

    @Test
    void b_readBooking() {
        Booking found = bookingService.read(savedBooking.getBookingId());
        assertNotNull(found);
        System.out.println("Booking read: " + new BookingDTO(found));
    }

    @Test
    void c_updateBooking() {
        Booking updated = new Booking.Builder()
                .copy(savedBooking)
                .setTotalAmount(1350)
                .build();

        Booking result = bookingService.update(updated);
        assertNotNull(result);

        System.out.println("Booking updated: " + new BookingDTO(result));
    }

    @Test
    void d_getAllBookings() {
        assertFalse(bookingService.getAllBookings().isEmpty());
        System.out.println("All bookings fetched successfully");
    }

    @Test
    void e_cancelBooking() {
        boolean cancelled = bookingService.delete(savedBooking.getBookingId());
        assertTrue(cancelled);

        System.out.println("Booking cancelled successfully (soft delete)");
    }
}
