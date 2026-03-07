package com.kwndtwalo.TogetherTransit.service.route;

import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.domain.route.RouteTimeSlot;
import com.kwndtwalo.TogetherTransit.repository.route.IRouteTimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteTimeSlotService implements IRouteTimeSlotService {

    private IRouteTimeSlotRepository repository;

    @Autowired
    public RouteTimeSlotService(IRouteTimeSlotRepository repository) {
        this.repository = repository;
    }

    /*
     * Business rules:
     * - Route must not be null
     * - Prevent duplicate identical time slots
     * - Prevent overlapping time slots on the same route
     */
    @Override
    public RouteTimeSlot create(RouteTimeSlot slot) {
        if (slot == null || slot.getRoute() == null) {
            return null;
        }

        Route route = slot.getRoute();

        // Prevent duplicate exact time slots.
        if (repository.findByRouteAndStartTimeAndEndTime(
                route,
                slot.getStartTime(),
                slot.getEndTime()
        )
        .isPresent()) {
            return null;
        }

        // Prevent overlapping slots.
        boolean overlaps = !repository
                .findByRouteAndStartTimeLessThanAndEndTimeGreaterThan(
                        route,
                        slot.getEndTime(),
                        slot.getStartTime()
                ).isEmpty();
        if (overlaps) {
            return null;
        }
        return repository.save(slot);
    }

    @Override
    public RouteTimeSlot read(Long Id) {
        return repository.findById(Id).orElse(null);
    }

    /*
     * UPDATE
     * Business rule:
     * - Slot must already exist
     */
    @Override
    public RouteTimeSlot update(RouteTimeSlot slot) {
        if (slot == null || slot.getSlotId() == null) {
            return null;
        }

        if (!repository.existsById(slot.getSlotId())) {
            return null;
        }
        return repository.save(slot);
    }

    @Override
    public List<RouteTimeSlot> getAllRouteTimeSlots() {
        return repository.findAll();
    }

    @Override
    public boolean delete(Long Id) {
        if(!repository.existsById(Id)) {
            return false;
        }
        repository.deleteById(Id);
        return true;
    }
}
