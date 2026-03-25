package com.kwndtwalo.TogetherTransit.controller.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.ChildBooking;
import com.kwndtwalo.TogetherTransit.dto.booking.ChildBookingDTO;
import com.kwndtwalo.TogetherTransit.service.booking.ChildBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/childBooking")
public class ChildBookingController {

    private final ChildBookingService childBookingService;

    @Autowired
    public ChildBookingController(ChildBookingService childBookingService) {
        this.childBookingService = childBookingService;
    }


    /**
     * CREATE CHILD BOOKING
     * -------------------------------------------------------
     * This endpoint assigns a child to an existing booking.
     *
     * Example scenario:
     * A parent creates a booking for a route,
     * then adds one or more children to that booking.
     *
     * Business Rules:
     * - ChildBooking must not be null
     * - A child cannot be added twice under the same booking
     *
     * The service layer checks duplicate prevention.
     */
    @PostMapping("/create")
    public ResponseEntity<ChildBookingDTO> create(@RequestBody ChildBooking childBooking) {

        ChildBooking created = childBookingService.create(childBooking);

        if (created == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new ChildBookingDTO(created));
    }


    /**
     * READ CHILD BOOKING BY ID
     * -------------------------------------------------------
     * Retrieves details for a specific child booking.
     *
     * Example use cases:
     * - Parent checking a child's transport schedule
     * - Admin reviewing transport assignments
     */
    @GetMapping("/read/{id}")
    public ResponseEntity<ChildBookingDTO> read(@PathVariable Long id) {

        ChildBooking booking = childBookingService.read(id);

        if (booking == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ChildBookingDTO(booking));
    }


    /**
     * UPDATE CHILD BOOKING
     * -------------------------------------------------------
     * Allows updating a child's transport configuration.
     *
     * Example updates:
     * - Change route
     * - Modify custom transport days
     * - Change booking status
     *
     * Business Rules:
     * - ChildBooking must exist
     * - childBookingId must be provided
     */
    @PutMapping("/update")
    public ResponseEntity<ChildBookingDTO> update(@RequestBody ChildBooking childBooking) {

        ChildBooking updated = childBookingService.update(childBooking);

        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new ChildBookingDTO(updated));
    }


    /**
     * GET ALL CHILD BOOKINGS
     * -------------------------------------------------------
     * Returns all child bookings in the system.
     *
     * Used for:
     * - Admin transport dashboards
     * - System monitoring
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<ChildBookingDTO>> getAllChildBookings() {

        List<ChildBookingDTO> bookings = childBookingService.getAllChildBookings()
                .stream()
                .map(ChildBookingDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookings);
    }


    /**
     * CANCEL CHILD BOOKING
     * -------------------------------------------------------
     * This performs a SOFT DELETE.
     *
     * Instead of removing the record from the database,
     * the status is changed to CANCELLED.
     *
     * Why Soft Delete?
     * - Maintains historical records
     * - Allows auditing
     * - Prevents data loss
     */
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Boolean> cancelChildBooking(@PathVariable Long id) {

        boolean cancelled = childBookingService.delete(id);

        return ResponseEntity.ok(cancelled);
    }

}