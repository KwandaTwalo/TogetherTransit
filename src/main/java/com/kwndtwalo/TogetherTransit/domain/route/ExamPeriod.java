package com.kwndtwalo.TogetherTransit.domain.route;

/* Purpose: flexible, temporary adjustments without changing monthly contract.
*
* This class sets the exam period.
* For example if June exams you set that they start from 28 May to 11 June then description is June exams.*/

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ExamPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long examPeriodId;

    //Added these to indicate when the exam period starts and ends
    private LocalDate startDate;
    private LocalDate endDate;

    private String description; //June exams 2025.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routeId")
    private Route route;

    @OneToMany(mappedBy = "examPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamSession> examSessions = new ArrayList<>();

    //CONSTRUCTORS
    protected ExamPeriod() {}

    private ExamPeriod(Builder builder) {
        this.examPeriodId = builder.examPeriodId;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.description = builder.description;
        this.route = builder.route;
        this.examSessions = builder.examSessions;
    }

    //GETTERS
    public Long getExamPeriodId() {
        return examPeriodId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public Route getRoute() {
        return route;
    }

    public List<ExamSession> getExamSessions() {
        return examSessions;
    }

    @Override
    public String toString() {
        return "ExamPeriod{" +
                "examPeriodId=" + getExamPeriodId() +
                ", startDate=" + getStartDate() +
                ", endDate=" + getEndDate() +
                ", description='" + getDescription() + '\'' +
                ", examSessions=" + getExamSessions() +
                ", route=" + getRoute() +
                '}';
    }

    public static class Builder {
        private Long examPeriodId;
        private LocalDate startDate;
        private LocalDate endDate;
        private String description;
        private Route route;
        private List<ExamSession> examSessions = new ArrayList<>();

        public Builder setExamPeriodId(Long examPeriodId) {
            this.examPeriodId = examPeriodId;
            return this;
        }
        public Builder setStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }
        public Builder setEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }
        public Builder setRoute(Route route) {
            this.route = route;
            return this;
        }
        public Builder setExamSession(ExamSession examSession) {
            this.examSessions.add(examSession);
            return this;
        }

        public Builder copy(ExamPeriod examPeriod) {
            this.examPeriodId = examPeriod.getExamPeriodId();
            this.startDate = examPeriod.getStartDate();
            this.endDate = examPeriod.getEndDate();
            this.description = examPeriod.getDescription();
            this.route = examPeriod.getRoute();
            this.examSessions = examPeriod.getExamSessions();
            return this;
        }

        public ExamPeriod build() {return new ExamPeriod(this);}

    }
}
