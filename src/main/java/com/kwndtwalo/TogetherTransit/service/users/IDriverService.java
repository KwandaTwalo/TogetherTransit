package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IDriverService extends IService<Driver, Long> {
    List<Driver> getAllDrivers();
}
