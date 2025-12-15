package com.kwndtwalo.TogetherTransit.domain.route;

/*This class defines specific time trips for that route.*/

/* This table supports:
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
}
