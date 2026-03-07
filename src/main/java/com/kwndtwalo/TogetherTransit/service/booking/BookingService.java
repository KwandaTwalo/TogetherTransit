package com.kwndtwalo.TogetherTransit.service.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.repository.booking.IBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService {

    private IBookingRepository bookingRepository;

    @Autowired
    public BookingService(IBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking create(Booking booking) {
        if (booking == null) {
            return null;
        }

        // Prevent duplicate booking for same parent + route + period.
        boolean exists = bookingRepository
                .existsByParentAndRouteAndContractStartDateLessThanEqualAndContractEndDateGreaterThanEqual(
                        booking.getParent(),
                        booking.getRoute(),
                        booking.getContractEndDate(),
                        booking.getContractStartDate()
                );

        if (exists) {
            throw new IllegalArgumentException("Parent already has an active booking for this route in the given period.");
        }

        //Default status if not provided.
        if (booking.getStatus() == null) {
            booking = new Booking.Builder()
                    .copy(booking)
                    .setStatus(Booking.BookingStatus.PENDING)
                    .build();
        }
        return bookingRepository.save(booking);
    }

    @Override
    public Booking read(Long Id) {
        return bookingRepository.findById(Id).orElse(null);
    }

    @Override
    public Booking update(Booking booking) {
        if (booking == null || booking.getBookingId() == null) { return null; }

        if (!bookingRepository.existsById(booking.getBookingId())) {return null; }

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /*
     * DELETE = soft delete (CANCEL)
     */
    @Override
    public boolean delete(Long Id) {

       Booking booking = read(Id);
       if (booking == null) {
           return false;
       }

       Booking cancelled = new Booking.Builder()
               .copy(booking)
               .setStatus(Booking.BookingStatus.CANCELLED)
               .build();

       bookingRepository.save(cancelled);
       return true;
    }

    // -------------------------
    // CUSTOM QUERY USAGE
    // -------------------------

    public List<Booking> getBookingsByParent(Long parentId) {
        return bookingRepository.findAll();
    }

    public long countActiveBookingsForRoute(Long routeId) {
        return bookingRepository.countByRouteAndStatus(
                read(routeId).getRoute(),
                Booking.BookingStatus.ACTIVE
        );
    }
}
