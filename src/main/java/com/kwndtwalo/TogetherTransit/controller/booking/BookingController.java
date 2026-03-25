package com.kwndtwalo.TogetherTransit.controller.booking;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.dto.booking.BookingDTO;
import com.kwndtwalo.TogetherTransit.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    /**
     * CREATE BOOKING
     * -------------------------------------------------------
     * Allows a parent to book transport for their child on a route.
     *
     * Business Rules:
     * - Booking object must not be null
     * - A parent cannot create multiple bookings for the same route
     *   within the same contract period
     * - Default booking status = PENDING
     *
     * Example Use Case:
     * Parent selects a route and submits a booking request.
     */
    @PostMapping("/create")
    public ResponseEntity<BookingDTO> create(@RequestBody Booking booking) {

        try {

            Booking createdBooking = bookingService.create(booking);

            if (createdBooking == null) {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok(new BookingDTO(createdBooking));

        } catch (IllegalArgumentException ex) {

            return ResponseEntity.badRequest().build();

        }
    }


    /**
     * READ BOOKING BY ID
     * -------------------------------------------------------
     * Retrieves a specific booking.
     *
     * Business Rules:
     * - Booking must exist in the database
     * - If booking is not found → return HTTP 404
     *
     * Example Use Case:
     * Parent checking booking details
     * Admin reviewing booking record
     */
    @GetMapping("/read/{id}")
    public ResponseEntity<BookingDTO> read(@PathVariable Long id) {

        Booking booking = bookingService.read(id);

        if (booking == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new BookingDTO(booking));
    }


    /**
     * UPDATE BOOKING
     * -------------------------------------------------------
     * Updates booking information.
     *
     * Business Rules:
     * - Booking must already exist
     * - bookingId must be provided
     *
     * Example Use Case:
     * Admin updating booking status
     * Parent modifying contract dates
     */
    @PutMapping("/update")
    public ResponseEntity<BookingDTO> update(@RequestBody Booking booking) {

        Booking updatedBooking = bookingService.update(booking);

        if (updatedBooking == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new BookingDTO(updatedBooking));
    }


    /**
     * GET ALL BOOKINGS
     * -------------------------------------------------------
     * Returns all bookings in the system.
     *
     * Used for:
     * - Admin dashboards
     * - System monitoring
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {

        List<BookingDTO> bookings = bookingService.getAllBookings()
                .stream()
                .map(BookingDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookings);
    }


    /**
     * CANCEL BOOKING
     * -------------------------------------------------------
     * Cancels an existing booking.
     *
     * Business Rule:
     * - Bookings are NOT permanently deleted
     * - Instead they are marked as CANCELLED (soft delete)
     *
     * Why soft delete?
     * - Keeps booking history
     * - Prevents data loss
     * - Allows auditing and reporting
     */
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Boolean> cancelBooking(@PathVariable Long id) {

        boolean cancelled = bookingService.delete(id);

        return ResponseEntity.ok(cancelled);
    }

}