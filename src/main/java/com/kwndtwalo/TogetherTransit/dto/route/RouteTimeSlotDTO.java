package com.kwndtwalo.TogetherTransit.dto.route;

import com.kwndtwalo.TogetherTransit.domain.route.RouteTimeSlot;

import java.time.LocalTime;

public class RouteTimeSlotDTO {

    private Long slotId;
    private RouteTimeSlot.TimeType timeType;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long routeId;

    public RouteTimeSlotDTO(RouteTimeSlot slot) {
        this.slotId = slot.getSlotId();
        this.timeType = slot.getTimeType();
        this.startTime = slot.getStartTime();
        this.endTime = slot.getEndTime();
        this.routeId = slot.getRoute() != null ? slot.getRoute().getRouteId() : null;
    }

    @Override
    public String toString() {
        return "RouteTimeSlotDTO{" +
                "slotId=" + slotId +
                ", timeType=" + timeType +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", routeId=" + routeId +
                '}';
    }
}
