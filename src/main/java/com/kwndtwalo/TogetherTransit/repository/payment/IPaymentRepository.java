package com.kwndtwalo.TogetherTransit.repository.payment;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.payment.Payment;
import com.kwndtwalo.TogetherTransit.domain.payment.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Long> {

    // ---------------------------------
    // BOOKING-RELATED QUERIES
    // ---------------------------------

    // Get all payments made for a specific booking
    List<Payment> findByBooking(Booking booking);

    // Get latest payment for a booking (monthly subscriptions)
    Optional<Payment> findFirstByBookingOrderByPaymentDateDesc(Booking booking);

    // Prevent duplicate payments for the same booking period
    boolean existsByBookingAndPaidForPeriodStartAndPaidForPeriodEnd(
            Booking booking,
            LocalDateTime start,
            LocalDateTime end
    );


    // ---------------------------------
    // PAYMENT STATUS & FAILURE HANDLING
    // ---------------------------------

    // Fetch all payments by status (SUCCESS, FAILED, PENDING, REFUNDED)
    List<Payment> findByStatus(Payment.PaymentStatus status);

    // Find all failed payments (retry logic / admin review)
    List<Payment> findByStatusAndFailureReasonIsNotNull(
            Payment.PaymentStatus status
    );


    // ---------------------------------
    // PAYMENT METHOD ANALYTICS
    // ---------------------------------

    // All payments made using a specific method (CARD, EFT, PAYPAL)
    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);


    // ---------------------------------
    // DATE-BASED REPORTING
    // ---------------------------------

    // Payments made within a specific time window
    List<Payment> findByPaymentDateBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    // Payments covering a specific subscription period
    List<Payment> findByPaidForPeriodStartGreaterThanEqualAndPaidForPeriodEndLessThanEqual(
            LocalDateTime start,
            LocalDateTime end
    );


    // ---------------------------------
    // FINANCIAL REPORTING
    // ---------------------------------

    // All successful payments (used for revenue calculations)
    List<Payment> findByStatusAndPaymentDateBetween(
            Payment.PaymentStatus status,
            LocalDateTime start,
            LocalDateTime end
    );
}
