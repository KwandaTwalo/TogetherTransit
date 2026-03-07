package com.kwndtwalo.TogetherTransit.service.payment;

import com.kwndtwalo.TogetherTransit.domain.payment.Payment;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IPaymentService extends IService<Payment, Long> {
    List<Payment> getAllPayments();
}
