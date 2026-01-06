package com.kwndtwalo.TogetherTransit.domain.route;

/*This class defines specific time trips for that route.*/

/** This table supports:
* Weekend classes.
* Early morning classes.
* Late classes.
* Multiple trips per day.
* Repeated daily scheduling.
* */

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class RouteTimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long slotId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routeId")
    private Route route;

    @Enumerated(EnumType.STRING)
    private TimeType timeType; // MORNING, AFTERNOON, EVENING, CUSTOM

    private LocalTime startTime;
    private LocalTime endTime;

    public enum TimeType {
        MORNING,
        AFTERNOON,
        EVENING,
        CUSTOM   // for unique cases
    }

    //CONSTRUCTORS
    protected RouteTimeSlot() {}

    private RouteTimeSlot(Builder builder) {
        this.slotId = builder.slotId;
        this.route = builder.route;
        this.timeType = builder.timeType;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
    }

    //GETTERS METHOD

    public Long getSlotId() {
        return slotId;
    }

    public Route getRoute() {
        return route;
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "RouteTimeSlot{" +
                "slotId=" + getSlotId() +
                ", timeType=" + getTimeType() +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                ", route=" + getRoute() +
                '}';
    }

    public static class Builder {
        private Long slotId;
        private Route route;
        private LocalTime startTime;
        private LocalTime endTime;
        private TimeType timeType;

        public Builder setSlotId(Long slotId) {
            this.slotId = slotId;
            return this;
        }
        public Builder setRoute(Route route) {
            this.route = route;
            return this;
        }
        public Builder setStartTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }
        public Builder setEndTime(LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }
        public Builder setTimeType(TimeType timeType) {
            this.timeType = timeType;
            return this;
        }

        public Builder copy(RouteTimeSlot routeTimeSlot) {
            this.slotId = routeTimeSlot.getSlotId();
            this.route = routeTimeSlot.getRoute();
            this.startTime = routeTimeSlot.getStartTime();
            this.endTime = routeTimeSlot.getEndTime();
            this.timeType = routeTimeSlot.getTimeType();
            return this;
        }

        public RouteTimeSlot build() {return new RouteTimeSlot(this);}
    }

}
