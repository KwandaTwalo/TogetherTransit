package com.kwndtwalo.TogetherTransit.repository.route;

import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;


@Repository
public interface IRouteRepository extends JpaRepository<Route, Long> {

    // ---------------------------------
    // DRIVER-BASED QUERIES
    // ---------------------------------

    // Fetch all routes assigned to a driver
    List<Route> findByDriver(Driver driver);

    // Fetch routes for a driver filtered by status
    List<Route> findByDriverAndStatus(Driver driver, Route.RouteStatus status);


    // ---------------------------------
    // SCHOOL-BASED QUERIES
    // ---------------------------------

    // Fetch all routes servicing a school
    List<Route> findBySchool(School school);

    // Fetch active routes for a school
    List<Route> findBySchoolAndStatus(School school, Route.RouteStatus status);


    // ---------------------------------
    // STATUS & LIFECYCLE
    // ---------------------------------

    // Fetch routes by status
    List<Route> findByStatus(Route.RouteStatus status);

    // Fetch all active monthly routes
    List<Route> findByIsMonthlyContractTrueAndStatus(Route.RouteStatus status);


    // ---------------------------------
    // SCHEDULING & DAYS
    // ---------------------------------

    // Fetch routes that operate on a specific day
    List<Route> findByServiceDaysContaining(DayOfWeek day);

    // Fetch active routes for a specific day
    List<Route> findByServiceDaysContainingAndStatus(
            DayOfWeek day,
            Route.RouteStatus status
    );


    // ---------------------------------
    // LOCATION / ROUTE DISCOVERY
    // ---------------------------------

    // Find routes by pickup and dropoff points (duplicate prevention / discovery)
    Optional<Route> findByPickupPointAndDropoffPointAndSchool(
            String pickupPoint,
            String dropoffPoint,
            School school
    );


    // ---------------------------------
    // PRICING / SUBSCRIPTION
    // ---------------------------------

    // Fetch all monthly subscription routes
    List<Route> findByIsMonthlyContractTrue();

    // Fetch routes within a price range
    List<Route> findByMonthlyFeeBetween(double minFee, double maxFee);

}
