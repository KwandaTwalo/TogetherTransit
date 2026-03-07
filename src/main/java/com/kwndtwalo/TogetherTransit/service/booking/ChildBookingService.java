package com.kwndtwalo.TogetherTransit.service.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.ChildBooking;
import com.kwndtwalo.TogetherTransit.repository.booking.IChildBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildBookingService implements IChildBookingService {

    private IChildBookingRepository childBookingRepository;

    @Autowired
    public ChildBookingService(IChildBookingRepository childBookingRepository) {
        this.childBookingRepository = childBookingRepository;
    }

    /**
     * Create a ChildBooking for a child under an existing Booking.
     * Business rules are enforced before saving.
     */
    @Override
    public ChildBooking create(ChildBooking childBooking) {

        if (childBooking == null) {
            return null;
        }

        //Business Rule: A child cannot be booked twice under the same booking.
        boolean exists = childBookingRepository.existsByBookingAndChild(
                childBooking.getBooking(),
                childBooking.getChild()
        );
        if (exists) {
            System.out.println("Child already exists under this booking");
            return null;
        }
        return childBookingRepository.save(childBooking);
    }

    @Override
    public ChildBooking read(Long Id) {
        return childBookingRepository.findById(Id).orElse(null);
    }

    @Override
    public ChildBooking update(ChildBooking childBooking) {

        if (childBooking == null || childBooking.getChildBookingId() == null) {
            return null;
        }

        //Ensure the record exists before updating.
        if (!childBookingRepository.existsById(childBooking.getChildBookingId())) {
            return null;
        }
        return childBookingRepository.save(childBooking);
    }

    @Override
    public List<ChildBooking> getAllChildBookings() {

        return childBookingRepository.findAll();
    }

    /**Soft delete: mark ChildBooking as CANCELLED.*/
    @Override
    public boolean delete(Long Id) {

        ChildBooking booking = read(Id);
        if (booking == null) {
            return false;
        }

        ChildBooking cancelled = new ChildBooking.Builder()
                .copy(booking)
                .setChildBookingStatus(ChildBooking.ChildBookingStatus.CANCELLED)
                .build();

        childBookingRepository.save(cancelled);
        return true;
    }
}
