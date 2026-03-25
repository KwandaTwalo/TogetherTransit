package com.kwndtwalo.TogetherTransit.controller.route;

import com.kwndtwalo.TogetherTransit.domain.route.RouteTimeSlot;
import com.kwndtwalo.TogetherTransit.dto.route.RouteTimeSlotDTO;
import com.kwndtwalo.TogetherTransit.service.route.RouteTimeSlotService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routeTimeSlot")
public class RouteTimeSlotController {

    private final RouteTimeSlotService routeTimeSlotService;

    @Autowired
    public RouteTimeSlotController(RouteTimeSlotService routeTimeSlotService) {
        this.routeTimeSlotService = routeTimeSlotService;
    }
    /**
     * CREATE ROUTE TIME SLOT
     * -------------------------------------------------------
     * Creates a new time slot for a route.
     *
     * Business Rules:
     * - RouteTimeSlot must not be null
     * - RouteTimeSlot must be associated with an existing route
     * - Prevent duplicate identical time slots for the same route
     * - Prevent overlapping time slots for the same route
     *
     * Example use cases:
     * - Driver sets a morning slot for a route: 7:00 AM - 8:00 AM
     * - Driver sets an afternoon slot for the same route: 3:00 PM - 4:00 PM
     */
    @PostMapping("/create")
    public ResponseEntity<RouteTimeSlotDTO> create(@RequestBody RouteTimeSlot routeTimeSlot) {
        
        RouteTimeSlot created = routeTimeSlotService.create(routeTimeSlot);

        if (created == null) { return ResponseEntity.badRequest().build(); }
        
        return ResponseEntity.ok(new RouteTimeSlotDTO(created));
    }

    @GetMapping("/read/{id}") 
    public ResponseEntity<RouteTimeSlotDTO> read(@PathVariable Long id) {

        RouteTimeSlot read = routeTimeSlotService.read(id);
        if (read == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new RouteTimeSlotDTO(read));
    }

    @PutMapping("/Update/{id}") 
    public ResponseEntity<RouteTimeSlotDTO> update(@RequestBody RouteTimeSlot routeTimeSlot) {
        RouteTimeSlot updated = routeTimeSlotService.update(routeTimeSlot);
        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new RouteTimeSlotDTO(updated));       
    } 
    
    @GetMapping("/getAll")
    public ResponseEntity<List<RouteTimeSlotDTO>> getAll() {
        List<RouteTimeSlot> all = routeTimeSlotService.getAllRouteTimeSlots();
        List<RouteTimeSlotDTO> dtos = all.stream()
                .map(RouteTimeSlotDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean deleted = routeTimeSlotService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deleted);
    }
    

}
