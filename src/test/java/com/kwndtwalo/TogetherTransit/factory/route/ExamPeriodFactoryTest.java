package com.kwndtwalo.TogetherTransit.factory.route;

import com.kwndtwalo.TogetherTransit.domain.route.ExamPeriod;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExamPeriodFactoryTest {

    private Route mockRoute = Mockito.mock(Route.class);

    @Test
    void createExamPeriod() {

        ExamPeriod examPeriod = ExamPeriodFactory.createExamPeriod(
                LocalDate.of(2025, 5, 28),
                LocalDate.of(2025, 6, 11),
                "June exams 2025",
                mockRoute
        );

        assertNotNull(examPeriod);
        assertEquals("June exams 2025", examPeriod.getDescription());
        assertEquals(LocalDate.of(2025, 5, 28), examPeriod.getStartDate());

        System.out.println("ExamPeriod created successfully:\n " + examPeriod);
    }

    @Test
    void createExamPeriod_invalidDateRange_returnsNull() {

        ExamPeriod examPeriod = ExamPeriodFactory.createExamPeriod(
                LocalDate.of(2025, 6, 15),
                LocalDate.of(2025, 6, 1), // end before start
                "Invalid exam period",
                mockRoute
        );

        assertNull(examPeriod);
        System.out.println("ExamPeriod creation failed due to invalid date range\n " + examPeriod);
    }


}