package com.kwndtwalo.TogetherTransit.service.route;

import com.kwndtwalo.TogetherTransit.domain.route.ExamPeriod;
import com.kwndtwalo.TogetherTransit.domain.route.ExamSession;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.dto.route.ExamSessionDTO;
import com.kwndtwalo.TogetherTransit.factory.auth.AuthenticationFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.AddressFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;
import com.kwndtwalo.TogetherTransit.factory.route.ExamPeriodFactory;
import com.kwndtwalo.TogetherTransit.factory.route.ExamSessionFactory;
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
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ExamSessionServiceTest {

    @Autowired
    private ExamSessionService examSessionService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ExamPeriodService examPeriodService;

    private static ExamSession savedSession;

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
        Route savedRoute = routeService.create(
                RouteFactory.createRoute(
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
                        "Exam transport"
                )
        );

        // ---- EXAM PERIOD ----
        ExamPeriod examPeriod = examPeriodService.create(
                ExamPeriodFactory.createExamPeriod(
                        LocalDate.now().plusDays(1),
                        LocalDate.now().plusDays(14),
                        "June Exams 2025",
                        savedRoute
                )
        );

        // ---- EXAM SESSION ----
        ExamSession session = ExamSessionFactory.createExamSession(
                LocalDate.now().plusDays(3),
                LocalTime.of(6, 30),
                LocalTime.of(8, 0),
                "Mathematics",
                ExamSession.PaperType.MORNING,
                savedRoute,
                examPeriod
        );

        savedSession = examSessionService.create(session);

        assertNotNull(savedSession);
        assertNotNull(savedSession.getExamSessionId());

        System.out.println("Created: " + new ExamSessionDTO(savedSession));
    }

    @Test
    void b_read() {
        ExamSession found = examSessionService.read(savedSession.getExamSessionId());
        assertNotNull(found);
        System.out.println("Read: " + new ExamSessionDTO(found));
    }

    @Test
    void c_update() {
        ExamSession updated = new ExamSession.Builder()
                .copy(savedSession)
                .setSubject("Advanced Mathematics")
                .build();

        ExamSession result = examSessionService.update(updated);
        assertNotNull(result);

        System.out.println("Updated: " + new ExamSessionDTO(result));
    }

    @Test
    void d_getAll() {
        List<ExamSession> sessions = examSessionService.getAllExamSessions();
        assertFalse(sessions.isEmpty());

        sessions.forEach(s -> System.out.println(new ExamSessionDTO(s)));
    }

    @Test
    void e_delete() {
        boolean deleted = examSessionService.delete(savedSession.getExamSessionId());
        assertTrue(deleted);
        System.out.println("Deleted exam session ID: " + savedSession.getExamSessionId());
    }
}