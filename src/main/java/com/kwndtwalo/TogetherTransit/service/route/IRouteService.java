package com.kwndtwalo.TogetherTransit.service.route;

import com.kwndtwalo.TogetherTransit.domain.route.Route;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IRouteService extends IService<Route, Long> {
    List<Route> getAllRoutes();
}
