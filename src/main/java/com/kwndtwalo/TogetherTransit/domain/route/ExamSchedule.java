package com.kwndtwalo.TogetherTransit.domain.route;

/* Purpose: flexible, temporary adjustments without changing monthly contract.
*
* For example During exams:
* School closes early (11:00). Solution: Overrides time slot.
* Some grades have classes only certain days.
* Evening study groups happen. Solution: SpecialDropOffTime at night.
* Weekend Sessions may occur.*/

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ExamSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long examScheduleId;

    private LocalDateTime specialPickupTime;
    private LocalDateTime specialDropOffTime;

    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routeId")
    private Route route;


}
