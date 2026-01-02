package com.kwndtwalo.TogetherTransit.factory.payment;

import com.kwndtwalo.TogetherTransit.domain.payment.PaymentMethod;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.util.Helper;

import java.time.LocalDateTime;

public class PaymentMethodFactory {

    public static PaymentMethod createPaymentMethod( PaymentMethod.MethodType methodType,
                                                     String provider,
                                                     String token,
                                                     String brand,
                                                     String lastFourDigits,
                                                     boolean isActive,
                                                     LocalDateTime createdAt,
                                                     Parent parent) {
        if (!Helper.isValidString(provider)
                || !Helper.isValidString(token)
                || !Helper.isValidCreatedAt( createdAt))
            return null;

        // CARD-specific validation
        if (methodType == PaymentMethod.MethodType.CARD) {
            if (!Helper.isValidCardBrand(brand)
                    || !Helper.isValidLastFourDigits(lastFourDigits)) {
                return null;
            }
        }

        return new PaymentMethod.Builder()
                .setMethodType(methodType)
                .setProvider(provider)
                .setToken(token)
                .setBrand(brand)
                .setLastFourDigits(lastFourDigits)
                .setIs_active(isActive)
                .setCreatedAt(createdAt)
                .setParent(parent)
                .build();
    }
}
