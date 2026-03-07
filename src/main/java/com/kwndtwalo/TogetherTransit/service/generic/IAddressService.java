package com.kwndtwalo.TogetherTransit.service.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IAddressService extends IService<Address, Long> {
    List<Address> getAllAddresses();
}
