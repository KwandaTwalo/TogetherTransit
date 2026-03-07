package com.kwndtwalo.TogetherTransit.dto.payment;

import com.kwndtwalo.TogetherTransit.domain.payment.PaymentMethod;

import java.time.LocalDateTime;

public class PaymentMethodDTO {

    private Long paymentMethodId;
    private PaymentMethod.MethodType methodType;
    private String provider;
    private String brand;
    private String lastFourDigits;
    private boolean active;
    private LocalDateTime createdAt;

    private Long parentId;

    public PaymentMethodDTO(PaymentMethod paymentMethod) {
        this.paymentMethodId = paymentMethod.getPaymentMethodId();
        this.methodType = paymentMethod.getMethodType();
        this.provider = paymentMethod.getProvider();
        this.brand = paymentMethod.getBrand();
        this.lastFourDigits = paymentMethod.getLastFourDigits();
        this.active = paymentMethod.getActive();
        this.createdAt = paymentMethod.getCreatedAt();
        this.parentId = paymentMethod.getParent() != null
                ? paymentMethod.getParent().getUserId()
                : null;
    }

    // getters only
}
