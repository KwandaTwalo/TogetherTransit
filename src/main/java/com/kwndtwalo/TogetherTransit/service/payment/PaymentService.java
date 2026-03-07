package com.kwndtwalo.TogetherTransit.service.payment;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.payment.Payment;
import com.kwndtwalo.TogetherTransit.repository.payment.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService implements IPaymentService {

    private IPaymentRepository paymentRepository;

    @Autowired
    public PaymentService(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /*
     * Business rules:
     * - Prevent duplicate payments for the same booking & period
     * - Payment must reference a booking
     */
    @Override
    public Payment create(Payment payment) {
        if (payment == null || payment.getBooking() == null) {
            return null;
        }

        boolean duplicateExists =
                paymentRepository.existsByBookingAndPaidForPeriodStartAndPaidForPeriodEnd(
                        payment.getBooking(),
                        payment.getPaidForPeriodStart(),
                        payment.getPaidForPeriodEnd()
                );

        if (duplicateExists) {
            return paymentRepository
                    .findFirstByBookingOrderByPaymentDateDesc(payment.getBooking())
                    .orElse(null);
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment read(Long Id) {
        return paymentRepository.findById(Id).orElse(null);
    }

    /*
     * Business rules:
     * - Payment must already exist
     * - Used for updating status (SUCCESS / FAILED / REFUNDED)
     */
    @Override
    public Payment update(Payment payment) {
        if (payment == null || payment.getPaymentId() == null) {
            return null;
        }

        if (!paymentRepository.existsById(payment.getPaymentId())) {
            return null;
        }
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }


    @Override
    public boolean delete(Long Id) {
        if (!paymentRepository.existsById(Id)) {
            return false;
        }
        paymentRepository.deleteById(Id);
        return true;
    }

    /*All payments for a booking*/
    public List<Payment> getPaymentsByBooking(Booking booking) {
        return paymentRepository.findByBooking(booking);
    }

    /*Latest payment (used for subscriptions */
    public Payment getLatestPaymentForBooking(Booking booking) {
        return paymentRepository
                .findFirstByBookingOrderByPaymentDateDesc(booking).orElse(null);
    }

    /*Failed payments for admin review*/
    public List<Payment> getFailedPayments() {
        return paymentRepository.findByStatusAndFailureReasonIsNotNull(
                Payment.PaymentStatus.FAILED
        );
    }

    /*Revenue reports*/
    public List<Payment> getSuccessfulPaymentsBetween(LocalDateTime start, LocalDateTime end) {
        return paymentRepository.findByPaymentDateBetween(start, end);
    }


}
