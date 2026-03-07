package com.kwndtwalo.TogetherTransit.service.payment;

import com.kwndtwalo.TogetherTransit.domain.payment.PaymentMethod;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IPaymentMethodService extends IService<PaymentMethod, Long> {
    List<PaymentMethod> getAllPaymentMethods();
}
