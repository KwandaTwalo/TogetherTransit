package com.kwndtwalo.TogetherTransit.factory.route;

import com.kwndtwalo.TogetherTransit.domain.route.ExamPeriod;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.util.Helper;

import java.time.LocalDate;

public class ExamPeriodFactory {

    public static ExamPeriod createExamPeriod(
            LocalDate startDate,
            LocalDate endDate,
            String description,
            Route route
    ) {
        if (!Helper.isValidDateRange(startDate, endDate) || !Helper.isValidString(description)) {
            return null;
        }

        return new ExamPeriod.Builder()
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setDescription(description)
                .setRoute(route)
                .build();
    }
}
