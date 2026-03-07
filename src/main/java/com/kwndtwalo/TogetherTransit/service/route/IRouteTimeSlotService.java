package com.kwndtwalo.TogetherTransit.service.route;

import com.kwndtwalo.TogetherTransit.domain.route.RouteTimeSlot;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IRouteTimeSlotService extends IService<RouteTimeSlot, Long> {
    List<RouteTimeSlot> getAllRouteTimeSlots();
}
