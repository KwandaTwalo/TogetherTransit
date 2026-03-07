package com.kwndtwalo.TogetherTransit.service.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IBookingService extends IService<Booking, Long> {
    List<Booking> getAllBookings();
}
