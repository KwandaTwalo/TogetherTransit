package com.kwndtwalo.TogetherTransit.service.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.ChildBooking;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IChildBookingService extends IService<ChildBooking, Long> {
    List<ChildBooking> getAllChildBookings();
}
