package com.kwndtwalo.TogetherTransit.domain.route;

/*Purpose:
* Answers: which day the student goes.
* What time they go.
* Is it in the Morning or Afternoon.
* No transport on non-exam days*/


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class ExamSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long examSessionId;

    private LocalDate examDate;

    private LocalTime pickupTime;
    private LocalTime dropOffTime;

    private String subject;   // Maths, Physics

    @Enumerated(EnumType.STRING)
    private PaperType paperType; // Morning / Afternoon

    public enum PaperType {
        MORNING,
        AFTERNOON,
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examPeriodId")
    private ExamPeriod examPeriod;

    //CONSTRUCTORS
    protected ExamSession() {}

    private ExamSession(Builder builder) {
        this.examSessionId = builder.examSessionId;
        this.examDate = builder.examDate;
        this.pickupTime = builder.pickupTime;
        this.dropOffTime = builder.dropOffTime;
        this.subject = builder.subject;
        this.paperType = builder.paperType;
        this.route = builder.route;
        this.examPeriod = builder.examPeriod;
    }

    public Long getExamSessionId() {
        return examSessionId;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public LocalTime getPickupTime() {
        return pickupTime;
    }

    public LocalTime getDropOffTime() {
        return dropOffTime;
    }

    public String getSubject() {
        return subject;
    }

    public PaperType getPaperType() {
        return paperType;
    }

    public Route getRoute() {
        return route;
    }

    public ExamPeriod getExamPeriod() {
        return examPeriod;
    }

    @Override
    public String toString() {
        return "ExamSession{" +
                "examSessionId=" + getExamSessionId() +
                ", examDate=" + getExamDate() +
                ", pickupTime=" + getPickupTime() +
                ", dropOffTime=" + getDropOffTime() +
                ", subject='" + getSubject() + '\'' +
                ", paperType=" + getPaperType() +
                ", route=" + getRoute() +
                ", examPeriod=" + getExamPeriod() +
                '}';
    }

    public static class Builder {
        private Long examSessionId;
        private LocalDate examDate;
        private LocalTime pickupTime;
        private LocalTime dropOffTime;
        private String subject;
        private PaperType paperType;
        private Route route;
        private ExamPeriod examPeriod;

        public Builder setExamSessionId(Long examSessionId) {
            this.examSessionId = examSessionId;
            return this;
        }
        public Builder setExamDate(LocalDate examDate) {
            this.examDate = examDate;
            return this;
        }
        public Builder setPickupTime(LocalTime pickupTime) {
            this.pickupTime = pickupTime;
            return this;
        }
        public Builder setDropOffTime(LocalTime dropOffTime) {
            this.dropOffTime = dropOffTime;
            return this;
        }
        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }
        public Builder setPaperType(PaperType paperType) {
            this.paperType = paperType;
            return this;
        }
        public Builder setRoute(Route route) {
            this.route = route;
            return this;
        }
        public Builder setExamPeriod(ExamPeriod examPeriod) {
            this.examPeriod = examPeriod;
            return this;
        }

        public Builder copy(ExamSession examSession) {
            this.examSessionId = examSession.getExamSessionId();
            this.examDate = examSession.getExamDate();
            this.pickupTime = examSession.getPickupTime();
            this.dropOffTime = examSession.getDropOffTime();
            this.subject = examSession.getSubject();
            this.paperType = examSession.getPaperType();
            this.route = examSession.getRoute();
            this.examPeriod = examSession.getExamPeriod();
            return this;
        }

        public ExamSession build() {return new ExamSession(this);}
    }
}

