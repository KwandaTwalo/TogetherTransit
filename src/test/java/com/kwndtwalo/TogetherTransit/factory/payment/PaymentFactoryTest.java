package com.kwndtwalo.TogetherTransit.factory.payment;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.payment.Payment;
import com.kwndtwalo.TogetherTransit.domain.payment.PaymentMethod;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PaymentFactoryTest {

    // Dummy objects (we are testing factory logic, not persistence)
    private Booking booking = mock(Booking.class);
    private PaymentMethod paymentMethod = mock(PaymentMethod.class);

    @Test
    void createPayment_success() {

        Payment payment = PaymentFactory.createPayment(
                1500.00,
                Payment.CurrencyType.ZAR,
                LocalDateTime.now(),
                LocalDateTime.now().minusDays(30),
                LocalDateTime.now(),
                Payment.PaymentStatus.SUCCESS,
                null,
                booking,
                paymentMethod
        );

        assertNotNull(payment);
        assertEquals(1500.00, payment.getTotalAmount());
        assertEquals(Payment.PaymentStatus.SUCCESS, payment.getStatus());
        System.out.println("Payment created successfully: " + payment);
    }

    @Test
    void createPayment_invalidAmount() {

        Payment payment = PaymentFactory.createPayment(
                -500.00, //  invalid
                Payment.CurrencyType.ZAR,
                LocalDateTime.now(),
                LocalDateTime.now().minusDays(30),
                LocalDateTime.now(),
                Payment.PaymentStatus.SUCCESS,
                null,
                booking,
                paymentMethod
        );

        assertNull(payment);
        System.out.println("Invalid amount paid: ");
    }

    @Test
    void createPayment_invalidPaymentPeriod() {

        Payment payment = PaymentFactory.createPayment(
                1000.00,
                Payment.CurrencyType.ZAR,
                LocalDateTime.now(),
                LocalDateTime.now(),              // start after end
                LocalDateTime.now().minusDays(5),
                Payment.PaymentStatus.SUCCESS,
                null,
                booking,
                paymentMethod
        );

        assertNull(payment);
        System.out.println("Invalid payment period paid. The end date should be after the start date: ");
    }

    @Test
    void createPayment_futurePaymentDate() {

        Payment payment = PaymentFactory.createPayment(
                1000.00,
                Payment.CurrencyType.ZAR,
                LocalDateTime.now().plusDays(1),  // future date
                LocalDateTime.now().minusDays(30),
                LocalDateTime.now(),
                Payment.PaymentStatus.SUCCESS,
                null,
                booking,
                paymentMethod
        );

        assertNull(payment);
        System.out.println("Payment date must not be in the future.");
    }

    @Test
    void createPayment_failedWithoutReason() {

        Payment payment = PaymentFactory.createPayment(
                800.00,
                Payment.CurrencyType.ZAR,
                LocalDateTime.now(),
                LocalDateTime.now().minusDays(30),
                LocalDateTime.now(),
                Payment.PaymentStatus.FAILED,
                null, //  required for FAILED
                booking,
                paymentMethod
        );

        assertNull(payment);
        System.out.println("When the payment status is FAILED, there must be a reason it cannot be null.");
    }

    @Test
    void createPayment_failedWithReason() {

        Payment payment = PaymentFactory.createPayment(
                800.00,
                Payment.CurrencyType.ZAR,
                LocalDateTime.now(),
                LocalDateTime.now().minusDays(30),
                LocalDateTime.now(),
                Payment.PaymentStatus.FAILED,
                "Insufficient funds",
                booking,
                paymentMethod
        );

        assertNotNull(payment);
        assertEquals(Payment.PaymentStatus.FAILED, payment.getStatus());
        assertEquals("Insufficient funds", payment.getFailureReason());
        System.out.println("When the payment status is FAILED, there must be a reason it cannot be null.");
    }
}
