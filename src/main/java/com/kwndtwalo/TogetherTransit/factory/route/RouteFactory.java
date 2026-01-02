package com.kwndtwalo.TogetherTransit.factory.route;

import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.util.Helper;

import java.time.DayOfWeek;
import java.util.Set;

public class RouteFactory {

    public static Route createRoute(
            String pickupPoint,
            String dropoffPoint,
            boolean isMonthlyContract,
            double monthlyFee,
            Double pickupLatitude,
            Double pickupLongitude,
            Double dropoffLatitude,
            Double dropoffLongitude,
            double estimatedDistanceKm,
            double estimatedDurationMinutes,
            Set<DayOfWeek> serviceDays,
            Route.RouteStatus status,
            Driver driver,
            School school,
            String specialInstructions
    ) {
        if (!Helper.isValidString(pickupPoint) || !Helper.isValidString(dropoffPoint)
                || !Helper.isValidMonthlyFee(isMonthlyContract, monthlyFee)
                || !Helper.isValidCoordinates(pickupLatitude, pickupLongitude)
                || !Helper.isValidCoordinates(dropoffLatitude, dropoffLongitude)
                || !Helper.isValidPositiveNumber(estimatedDistanceKm)
                || !Helper.isValidPositiveNumber(estimatedDurationMinutes)
                || !Helper.isValidServiceDays(serviceDays))
        {return null;}

        return new Route.Builder()
                .setPickupPoint(pickupPoint)
                .setDropoffPoint(dropoffPoint)
                .setIsMonthlyContract(isMonthlyContract)
                .setMonthlyFee(monthlyFee)
                .setPickupLatitude(pickupLatitude)
                .setPickupLongitude(pickupLongitude)
                .setDropoffLatitude(dropoffLatitude)
                .setDropoffLongitude(dropoffLongitude)
                .setEstimatedDistanceKm(estimatedDistanceKm)
                .setEstimatedDurationMinutes(estimatedDurationMinutes)
                .setServiceDays(serviceDays)
                .setStatus(status)
                .setDriver(driver)
                .setSchool(school)
                .setSpecialInstructions(specialInstructions)
                .build();
    }
}
