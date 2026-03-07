package com.kwndtwalo.TogetherTransit.dto.route;

import com.kwndtwalo.TogetherTransit.domain.route.ExamSession;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExamSessionDTO {

    private Long examSessionId;
    private LocalDate examDate;
    private LocalTime pickupTime;
    private LocalTime dropOffTime;
    private String subject;
    private ExamSession.PaperType paperType;
    private Long routeId;
    private Long examPeriodId;

    public ExamSessionDTO(ExamSession session) {
        this.examSessionId = session.getExamSessionId();
        this.examDate = session.getExamDate();
        this.pickupTime = session.getPickupTime();
        this.dropOffTime = session.getDropOffTime();
        this.subject = session.getSubject();
        this.paperType = session.getPaperType();
        this.routeId = session.getRoute() != null
                ? session.getRoute().getRouteId()
                : null;
        this.examPeriodId = session.getExamPeriod() != null
                ? session.getExamPeriod().getExamPeriodId()
                : null;
    }

    @Override
    public String toString() {
        return "ExamSessionDTO{" +
                "examSessionId=" + examSessionId +
                ", examDate=" + examDate +
                ", pickupTime=" + pickupTime +
                ", dropOffTime=" + dropOffTime +
                ", subject='" + subject + '\'' +
                ", paperType=" + paperType +
                ", routeId=" + routeId +
                ", examPeriodId=" + examPeriodId +
                '}';
    }
}
