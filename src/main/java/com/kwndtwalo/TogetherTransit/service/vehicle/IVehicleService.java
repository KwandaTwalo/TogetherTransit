package com.kwndtwalo.TogetherTransit.service.vehicle;

import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.domain.vehicle.Vehicle;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IVehicleService extends IService<Vehicle, Long> {
    List<Vehicle> getAllVehicles();
}
