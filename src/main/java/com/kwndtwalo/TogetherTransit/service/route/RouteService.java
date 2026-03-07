package com.kwndtwalo.TogetherTransit.service.route;

import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.repository.route.IRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RouteService implements IRouteService {

    private IRouteRepository routeRepository;

    @Autowired
    public RouteService(IRouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    /*
     * CREATE ROUTE
     *
     * Business rules:
     * 1. Route object must not be null
     * 2. Prevent duplicate routes for the same school
     * 3. Default status = SCHEDULED
     */
    @Override
    public Route create(Route route) {
        if (route == null) {
            return null;
        }

        // Prevent duplicate pickup & drop off for same school.
        boolean exists = routeRepository
                .findByPickupPointAndDropoffPointAndSchool(
                        route.getPickupPoint(),
                        route.getDropoffPoint(),
                        route.getSchool()
                )
                .isPresent();

        if (exists) {
            return null; //duplicate route.
        }
        //Default lifecycle state.
        if (route.getSchool() == null) {
            route = new Route.Builder()
                    .copy(route)
                    .setStatus(Route.RouteStatus.SCHEDULED)
                    .build();
        }
        return routeRepository.save(route);
    }

    @Override
    public Route read(Long Id) {
        return routeRepository.findById(Id).orElse(null);
    }

    /*
     * UPDATE ROUTE
     *
     * Business rules:
     * 1. Route must exist
     * 2. COMPLETED or CANCELLED routes cannot be modified
     */
    @Override
    public Route update(Route route) {
        if (route == null || route.getRouteId() == null) {
            return null;
        }

        Route existing = routeRepository.findById(route.getRouteId()).orElse(null);
        if (existing == null) {
            return null;
        }

        if (existing.getStatus() == Route.RouteStatus.COMPLETED
        || existing.getStatus() == Route.RouteStatus.CANCELLED) {
            return null;
        }

        return routeRepository.save(route);
    }

    @Override
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    /*
     * DELETE ROUTE
     *
     * Business rule:
     * - Only SCHEDULED routes can be deleted
     */
    @Override
    public boolean delete(Long Id) {
        Route route = routeRepository.findById(Id).orElse(null);
        if (route == null) {
            return false;
        }

        if (route.getStatus() != Route.RouteStatus.SCHEDULED) {
            return false;
        }

        routeRepository.deleteById(Id);
        return true;
    }
}
