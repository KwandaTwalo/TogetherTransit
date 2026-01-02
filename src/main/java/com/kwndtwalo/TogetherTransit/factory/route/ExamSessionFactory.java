package com.kwndtwalo.TogetherTransit.factory.route;

import com.kwndtwalo.TogetherTransit.domain.route.ExamPeriod;
import com.kwndtwalo.TogetherTransit.domain.route.ExamSession;
import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.util.Helper;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExamSessionFactory {

        public static ExamSession createExamSession(
                LocalDate examDate,
                LocalTime pickupTime,
                LocalTime dropOffTime,
                String subject,
                ExamSession.PaperType paperType,
                Route route,
                ExamPeriod examPeriod
        ) {

            // Validate core fields
            if (!Helper.isValidExamDate(examDate)
                    || !Helper.isValidExamTimes(pickupTime, dropOffTime)
                    || !Helper.isValidString(subject)
                    || !Helper.isExamDateWithinPeriod(
                    examDate,
                    examPeriod.getStartDate(),
                    examPeriod.getEndDate()
            )) {
                return null;
            }

            return new ExamSession.Builder()
                    .setExamDate(examDate)
                    .setPickupTime(pickupTime)
                    .setDropOffTime(dropOffTime)
                    .setSubject(subject)
                    .setPaperType(paperType)
                    .setRoute(route)
                    .setExamPeriod(examPeriod)
                    .build();
        }
    }

