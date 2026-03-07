package com.kwndtwalo.TogetherTransit.dto.payment;

import com.kwndtwalo.TogetherTransit.domain.payment.Payment;

import java.time.LocalDateTime;

public class PaymentDTO {

    private Long paymentId;
    private double totalAmount;
    private Payment.CurrencyType currencyType;
    private LocalDateTime paymentDate;
    private LocalDateTime paidForPeriodStart;
    private LocalDateTime paidForPeriodEnd;
    private Payment.PaymentStatus status;
    private String failureReason;

    // Flattened references
    private Long bookingId;
    private Long paymentMethodId;

    public PaymentDTO(Payment payment) {
        this.paymentId = payment.getPaymentId();
        this.totalAmount = payment.getTotalAmount();
        this.currencyType = payment.getCurrencyType();
        this.paymentDate = payment.getPaymentDate();
        this.paidForPeriodStart = payment.getPaidForPeriodStart();
        this.paidForPeriodEnd = payment.getPaidForPeriodEnd();
        this.status = payment.getStatus();
        this.failureReason = payment.getFailureReason();
        this.bookingId = payment.getBooking() != null
                ? payment.getBooking().getBookingId()
                : null;
        this.paymentMethodId = payment.getPaymentMethod() != null
                ? payment.getPaymentMethod().getPaymentMethodId()
                : null;
    }

    // getters only (DTOs are immutable by design)
}
