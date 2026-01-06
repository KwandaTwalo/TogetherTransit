package com.kwndtwalo.TogetherTransit.repository.route;

import com.kwndtwalo.TogetherTransit.domain.route.ExamPeriod;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IExamPeriodRepository extends JpaRepository<ExamPeriod, Long> {

    // ---------------------------------
    // ROUTE-BASED QUERIES
    // ---------------------------------

    // Fetch all exam periods for a route
    List<ExamPeriod> findByRoute(Route route);

    // Fetch exam periods for a route ordered by start date
    List<ExamPeriod> findByRouteOrderByStartDateAsc(Route route);


    // ---------------------------------
    // DATE-BASED LOGIC
    // ---------------------------------


    // Fetch all exam periods overlapping a given range (safety check)
    List<ExamPeriod> findByRouteAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Route route,
            LocalDate endDate,
            LocalDate startDate
    );


    // ---------------------------------
    // DUPLICATE / CONFLICT PREVENTION
    // ---------------------------------

    // Prevent duplicate exam period definitions
    Optional<ExamPeriod> findByRouteAndStartDateAndEndDate(
            Route route,
            LocalDate startDate,
            LocalDate endDate
    );
}
