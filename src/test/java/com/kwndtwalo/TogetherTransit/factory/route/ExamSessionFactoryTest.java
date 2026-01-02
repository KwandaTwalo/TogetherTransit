package com.kwndtwalo.TogetherTransit.factory.route;

import com.kwndtwalo.TogetherTransit.domain.route.ExamPeriod;
import com.kwndtwalo.TogetherTransit.domain.route.ExamSession;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ExamSessionFactoryTest {

    private Route mockRoute = Mockito.mock(Route.class);
    private ExamPeriod mockExamPeriod = new ExamPeriod.Builder()
            .setStartDate(LocalDate.of(2025, 6, 1))
            .setEndDate(LocalDate.of(2025, 6, 15))
            .setRoute(mockRoute)
            .build();


    @Test
    void createExamSession() {

        ExamSession session = ExamSessionFactory.createExamSession(
                LocalDate.of(2025, 6, 5),
                LocalTime.of(6, 30),
                LocalTime.of(7, 30),
                "Mathematics",
                ExamSession.PaperType.MORNING,
                mockRoute,
                mockExamPeriod
        );

        assertNotNull(session);
        assertEquals("Mathematics", session.getSubject());
        assertEquals(ExamSession.PaperType.MORNING, session.getPaperType());

        System.out.println("ExamSession created successfully within exam period: \n" + session);
    }

    @Test
    void createExamSession_failsOutsideExamPeriod() {

        ExamSession session = ExamSessionFactory.createExamSession(
                LocalDate.of(2025, 7, 1), // outside range
                LocalTime.of(6, 30),
                LocalTime.of(7, 30),
                "Physics",
                ExamSession.PaperType.AFTERNOON,
                mockRoute,
                mockExamPeriod
        );

        assertNull(session);
        System.out.println("The exam is outside the exam period\n" + session);
    }

    @Test
    void createExamSession_failsInvalidTimes() {

        ExamSession session = ExamSessionFactory.createExamSession(
                LocalDate.of(2025, 6, 5),
                LocalTime.of(8, 0),
                LocalTime.of(7, 0), // invalid
                "Chemistry",
                ExamSession.PaperType.MORNING,
                mockRoute,
                mockExamPeriod
        );

        assertNull(session);
        System.out.println("The dropOff time is before the pickup time\n" + session);
    }

}