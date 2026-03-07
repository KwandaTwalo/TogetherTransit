package com.kwndtwalo.TogetherTransit.service.route;

import com.kwndtwalo.TogetherTransit.domain.route.ExamPeriod;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.dto.route.ExamPeriodDTO;
import com.kwndtwalo.TogetherTransit.factory.auth.AuthenticationFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.AddressFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;
import com.kwndtwalo.TogetherTransit.factory.route.ExamPeriodFactory;
import com.kwndtwalo.TogetherTransit.factory.route.RouteFactory;
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
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ExamPeriodServiceTest {

    @Autowired
    private ExamPeriodService examPeriodService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private RoleService roleService;

    private static ExamPeriod savedExamPeriod;

    @Test
    void a_create() {

        // ---- DRIVER ----
        Role role = roleService.getByRoleName(Role.RoleName.DRIVER);

        Driver driver = DriverFactory.createDriver(
                "12345678910",
                3,
                "Sandile",
                "Sibiya",
                LocalDate.now(),
                User.AccountStatus.ACTIVE,
                ContactFactory.createContact("0711111111", "0799999999"),
                AddressFactory.createAddress("12", "Main Road", "Claremont", "Cape Town", 7708),
                AuthenticationFactory.createAuthentication(
                        "driver2@gmail.com",
                        "Password123!",
                        LocalDateTime.now(),
                        false
                ),
                role
        );

        Driver savedDriver = driverService.create(driver);

        // ---- SCHOOL ----
        School school = schoolService.create(
                SchoolFactory.createSchool("Kwa-Komani Comprehensive School")
        );

        // ---- ROUTE ----
        Route route = routeService.create(
                RouteFactory.createRoute(
                        "Home - Exam Route",
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
                        school,
                        "Exam transport only"
                )
        );

        // ---- EXAM PERIOD ----
        ExamPeriod examPeriod = ExamPeriodFactory.createExamPeriod(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(14),
                "June Exams 2025",
                route
        );

        savedExamPeriod = examPeriodService.create(examPeriod);

        assertNotNull(savedExamPeriod);
        assertNotNull(savedExamPeriod.getExamPeriodId());

        System.out.println("Created: " + new ExamPeriodDTO(savedExamPeriod));
    }

    @Test
    void b_read() {
        ExamPeriod found = examPeriodService.read(savedExamPeriod.getExamPeriodId());
        assertNotNull(found);
        System.out.println("Read: " + new ExamPeriodDTO(found));
    }

    @Test
    void c_update() {
        ExamPeriod updated = new ExamPeriod.Builder()
                .copy(savedExamPeriod)
                .setDescription("Updated June Exams")
                .build();

        ExamPeriod result = examPeriodService.update(updated);
        assertNotNull(result);

        System.out.println("Updated: " + new ExamPeriodDTO(result));
    }

    @Test
    void d_getAll() {
        List<ExamPeriod> periods = examPeriodService.getAllExamPeriods();
        assertFalse(periods.isEmpty());

        periods.forEach(p -> System.out.println(new ExamPeriodDTO(p)));
    }

    @Test
    void e_delete() {
        boolean deleted = examPeriodService.delete(savedExamPeriod.getExamPeriodId());
        assertTrue(deleted);

        System.out.println("Deleted ExamPeriod ID: " + savedExamPeriod.getExamPeriodId());
    }
}