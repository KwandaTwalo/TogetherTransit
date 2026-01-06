package com.kwndtwalo.TogetherTransit.repository.route;

import com.kwndtwalo.TogetherTransit.domain.route.ExamPeriod;
import com.kwndtwalo.TogetherTransit.domain.route.ExamSession;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IExamSessionRepository extends JpaRepository<ExamSession, Long> {

    // ---------------------------------
    // PERIOD-BASED QUERIES
    // ---------------------------------

    // Fetch all sessions for an exam period
    List<ExamSession> findByExamPeriod(ExamPeriod examPeriod);

    // Fetch all sessions for an exam period ordered by date
    List<ExamSession> findByExamPeriodOrderByExamDateAsc(ExamPeriod examPeriod);


    // ---------------------------------
    // ROUTE & DATE QUERIES
    // ---------------------------------

    // Fetch all exam sessions for a route on a specific date
    List<ExamSession> findByRouteAndExamDate(Route route, LocalDate examDate);

    // Fetch all exam sessions for a route within a date range
    List<ExamSession> findByRouteAndExamDateBetween(
            Route route,
            LocalDate startDate,
            LocalDate endDate
    );


    // ---------------------------------
    // TIME & PAPER TYPE
    // ---------------------------------

    // Fetch MORNING / AFTERNOON sessions for a route on a date
    List<ExamSession> findByRouteAndExamDateAndPaperType(
            Route route,
            LocalDate examDate,
            ExamSession.PaperType paperType
    );


    // ---------------------------------
    // OVERLAP & DUPLICATE SAFETY
    // ---------------------------------

    // Detect overlapping exam sessions (same route, same day)
    List<ExamSession> findByRouteAndExamDateAndPickupTimeLessThanAndDropOffTimeGreaterThan(
            Route route,
            LocalDate examDate,
            LocalTime dropOffTime,
            LocalTime pickupTime
    );

    // Prevent duplicate session creation
    Optional<ExamSession> findByRouteAndExamDateAndPickupTimeAndPaperType(
            Route route,
            LocalDate examDate,
            LocalTime pickupTime,
            ExamSession.PaperType paperType
    );
}
