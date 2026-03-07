package com.kwndtwalo.TogetherTransit.dto.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.ChildBooking;

import java.time.DayOfWeek;
import java.util.Set;

public class ChildBookingDTO {

    private Long childBookingId;
    private boolean usesCustomSchedule;
    private Set<DayOfWeek> customDays;
    private String status;
    private String childName;
    private Long bookingId;
    private Long routeId;

    public ChildBookingDTO(ChildBooking childBooking) {

        this.childBookingId = childBooking.getChildBookingId();
        this.usesCustomSchedule = childBooking.getUsesCustomSchedule();
        this.customDays = childBooking.getCustomDays();
        this.status = childBooking.getChildBookingStatus().name();

        // Safe extraction (avoids lazy crashes)
        this.childName = childBooking.getChild() != null
                ? childBooking.getChild().getFirstName() + " " + childBooking.getChild().getLastName()
                : "N/A";

        this.bookingId = childBooking.getBooking() != null
                ? childBooking.getBooking().getBookingId()
                : null;

        this.routeId = childBooking.getRoute() != null
                ? childBooking.getRoute().getRouteId()
                : null;
    }

    @Override
    public String toString() {
        return "\nChildBookingDTO{" +
                "childBookingId=" + childBookingId +
                ", usesCustomSchedule=" + usesCustomSchedule +
                ", customDays=" + customDays +
                ", status='" + status + '\'' +
                ", childName='" + childName + '\'' +
                ", bookingId=" + bookingId +
                ", routeId=" + routeId +
                '}';
    }
}