package com.kwndtwalo.TogetherTransit.dto.route;

import com.kwndtwalo.TogetherTransit.domain.route.ExamPeriod;

import java.time.LocalDate;

public class ExamPeriodDTO {

    private Long examPeriodId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Long routeId;
    private int totalSessions;

    public ExamPeriodDTO(ExamPeriod examPeriod) {
        this.examPeriodId = examPeriod.getExamPeriodId();
        this.startDate = examPeriod.getStartDate();
        this.endDate = examPeriod.getEndDate();
        this.description = examPeriod.getDescription();
        this.routeId = examPeriod.getRoute() != null
                ? examPeriod.getRoute().getRouteId()
                : null;
        /*this.totalSessions = examPeriod.getExamSessions() != null
                ? examPeriod.getExamSessions().size()
                : 0;*/ //Commented out this it causes lazy or no session.
    }

    @Override
    public String toString() {
        return "ExamPeriodDTO{" +
                "examPeriodId=" + examPeriodId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", routeId=" + routeId +
                //", totalSessions=" + totalSessions +
                '}';
    }
}
