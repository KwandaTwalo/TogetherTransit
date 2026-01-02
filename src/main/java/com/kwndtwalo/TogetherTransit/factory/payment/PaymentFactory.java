package com.kwndtwalo.TogetherTransit.factory.payment;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import com.kwndtwalo.TogetherTransit.domain.payment.Payment;
import com.kwndtwalo.TogetherTransit.domain.payment.PaymentMethod;
import com.kwndtwalo.TogetherTransit.util.Helper;

import java.time.LocalDateTime;

public class PaymentFactory {

    public static Payment createPayment(double totalAmount,
                                        Payment.CurrencyType currencyType,
                                        LocalDateTime paymentDate,
                                        LocalDateTime paidForPeriodStart,
                                        LocalDateTime paidForPeriodEnd,
                                        Payment.PaymentStatus status,
                                        String failureReason,
                                        Booking booking,
                                        PaymentMethod paymentMethod) {
        if (!Helper.isValidPaymentAmount(totalAmount)
                || !Helper.isValidPaymentDate(paymentDate)
                || !Helper.isValidPaymentPeriod(paidForPeriodStart, paidForPeriodEnd)
                || !Helper.isValidFailureReason(failureReason, status)) {
            return null;
        }

        return new Payment.Builder()
                .setTotalAmount(totalAmount)
                .setCurrencyType(currencyType)
                .setPaymentDate(paymentDate)
                .setPaidForPeriodStart(paidForPeriodStart)
                .setPaidForPeriodEnd(paidForPeriodEnd)
                .setStatus(status)
                .setFailureReason(failureReason)
                .setBooking(booking)
                .setPaymentMethod(paymentMethod)
                .build();
    }
}
