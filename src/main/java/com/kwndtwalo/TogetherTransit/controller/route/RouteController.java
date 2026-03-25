package com.kwndtwalo.TogetherTransit.controller.route;

import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.dto.route.RouteDTO;
import com.kwndtwalo.TogetherTransit.service.route.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/route")
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    /**
     * CREATE ROUTE
     * -------------------------------------------------------
     * Creates a new transport route.

     * Business Rules:
     * - Route must not be null
     * - Duplicate routes (same pickup + dropoff + school) are not allowed
     * - Default status = SCHEDULED
     *
     * Example:
     * Driver creates a route:
     * Khayelitsha → Rondebosch Boys High School
     */
    @PostMapping("/create")
    public ResponseEntity<RouteDTO> create(@RequestBody Route route) {

        Route createdRoute = routeService.create(route);

        if (createdRoute == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new RouteDTO(createdRoute));
    }


    /**
     * READ ROUTE BY ID
     * -------------------------------------------------------
     * Fetches a specific route.
     *
     * Example use cases:
     * - Parent viewing route details
     * - Admin reviewing route configuration
     */
    @GetMapping("/read/{id}")
    public ResponseEntity<RouteDTO> read(@PathVariable Long id) {

        Route route = routeService.read(id);

        if (route == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new RouteDTO(route));
    }


    /**
     * UPDATE ROUTE
     * -------------------------------------------------------
     * Updates route details.
     *
     * Business Rules:
     * - Route must exist
     * - COMPLETED or CANCELLED routes cannot be modified
     *
     * Example updates:
     * - Change service days
     * - Update monthly fee
     * - Modify pickup point
     */
    @PutMapping("/update")
    public ResponseEntity<RouteDTO> update(@RequestBody Route route) {

        Route updatedRoute = routeService.update(route);

        if (updatedRoute == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new RouteDTO(updatedRoute));
    }


    /**
     * GET ALL ROUTES
     * -------------------------------------------------------
     * Returns all routes in the system.
     *
     * Used for:
     * - Parent route discovery
     * - Admin dashboards
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<RouteDTO>> getAllRoutes() {

        List<RouteDTO> routes = routeService.getAllRoutes()
                .stream()
                .map(RouteDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(routes);
    }


    /**
     * DELETE ROUTE
     * -------------------------------------------------------
     * Removes a route from the system.
     *
     * Business Rules:
     * - Only routes with status SCHEDULED can be deleted
     *
     * Why?
     * If a route is ACTIVE or COMPLETED, it may already
     * have bookings attached to it.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {

        boolean deleted = routeService.delete(id);

        return ResponseEntity.ok(deleted);
    }

}