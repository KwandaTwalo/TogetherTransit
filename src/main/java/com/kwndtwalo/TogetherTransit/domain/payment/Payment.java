package com.kwndtwalo.TogetherTransit.domain.payment;

import com.kwndtwalo.TogetherTransit.domain.booking.Booking;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    // Core payment info
    private double totalAmount;
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType; // "ZAR", "USD"

    public enum CurrencyType {
        ZAR, USD, EUR
    }

    //This only answers when and what time the parent made a payment. Does not answer for which month.
    private LocalDateTime paymentDate;

    // These two attributes indicate current payment made is from this date until that date (Service period).
    private LocalDateTime paidForPeriodStart;
    private LocalDateTime paidForPeriodEnd;

    private String failureReason;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public enum PaymentStatus {
        PENDING,
        SUCCESS,
        FAILED,
        REFUNDED
    }

    // Relationship
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookingId")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentMethodId")
    private PaymentMethod paymentMethod;

    protected Payment() {}

    private Payment(Builder builder) {
        this.paymentId = builder.paymentId;
        this.totalAmount = builder.totalAmount;
        this.currencyType = builder.currencyType;
        this.paymentDate = builder.paymentDate;
        this.paidForPeriodStart = builder.paidForPeriodStart;
        this.paidForPeriodEnd = builder.paidForPeriodEnd;
        this.failureReason = builder.failureReason;
        this.status = builder.status;
        this.booking = builder.booking;
        this.paymentMethod = builder.paymentMethod;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public LocalDateTime getPaidForPeriodStart() {
        return paidForPeriodStart;
    }

    public LocalDateTime getPaidForPeriodEnd() {
        return paidForPeriodEnd;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Booking getBooking() {
        return booking;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + getPaymentId() +
                ", totalAmount=" + getTotalAmount() +
                ", currencyType=" + getCurrencyType() +
                ", paymentDate=" + getPaymentDate() +
                ", paidForPeriodStart=" + getPaidForPeriodStart() +
                ", paidForPeriodEnd=" + getPaidForPeriodEnd() +
                ", failureReason='" + getFailureReason() + '\'' +
                ", status=" + getStatus() +
                ", booking=" + getBooking() +
                ", paymentMethod=" + getPaymentMethod() +
                '}';
    }

    public static class Builder {
        private Long paymentId;
        private double totalAmount;
        private CurrencyType currencyType;
        private LocalDateTime paymentDate;
        private LocalDateTime paidForPeriodStart;
        private LocalDateTime paidForPeriodEnd;
        private String failureReason;
        private PaymentStatus status;
        private Booking booking;
        private PaymentMethod paymentMethod;

        public Builder setPaymentId(Long paymentId) {
            this.paymentId = paymentId;
            return this;
        }
        public Builder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }
        public Builder setCurrencyType(CurrencyType currencyType) {
            this.currencyType = currencyType;
            return this;
        }
        public Builder setPaymentDate(LocalDateTime paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }
        public Builder setPaidForPeriodStart(LocalDateTime paidForPeriodStart) {
            this.paidForPeriodStart = paidForPeriodStart;
            return this;
        }
        public Builder setPaidForPeriodEnd(LocalDateTime paidForPeriodEnd) {
            this.paidForPeriodEnd = paidForPeriodEnd;
            return this;
        }
        public Builder setFailureReason(String failureReason) {
            this.failureReason = failureReason;
            return this;
        }
        public Builder setStatus(PaymentStatus status) {
            this.status = status;
            return this;
        }
        public Builder setBooking(Booking booking) {
            this.booking = booking;
            return this;
        }
        public Builder setPaymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder copy(Payment payment) {
            this.paymentId = payment.getPaymentId();
            this.totalAmount = payment.getTotalAmount();
            this.currencyType = payment.getCurrencyType();
            this.paymentDate = payment.getPaymentDate();
            this.paidForPeriodStart = payment.getPaidForPeriodStart();
            this.paidForPeriodEnd = payment.getPaidForPeriodEnd();
            this.failureReason = payment.getFailureReason();
            this.status = payment.getStatus();
            this.booking = payment.getBooking();
            this.paymentMethod = payment.getPaymentMethod();
            return this;
        }
        public Payment build() {return new Payment(this);}

    }
}
