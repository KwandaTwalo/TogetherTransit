package com.kwndtwalo.TogetherTransit.factory.payment;

import com.kwndtwalo.TogetherTransit.domain.payment.PaymentMethod;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PaymentMethodFactoryTest {

    private Parent mockParent = mock(Parent.class);

    @Test
    void createPaymentMethod_success_card() {

        PaymentMethod method = PaymentMethodFactory.createPaymentMethod(
                PaymentMethod.MethodType.CARD,
                "Stripe",
                "tok_12345678901234567890",
                "Visa",
                "1234",
                true,
                LocalDateTime.now(),
                mockParent
        );

        assertNotNull(method);
        assertEquals("Visa", method.getBrand());
        assertEquals("1234", method.getLastFourDigits());
        System.out.println("PaymentMethod created successfully: " + method);
    }

    @Test
    void createPaymentMethod_invalidLastFourDigits() {

        PaymentMethod method = PaymentMethodFactory.createPaymentMethod(
                PaymentMethod.MethodType.CARD,
                "Stripe",
                "tok_12345678901234567890",
                "Visa",
                "12", // invalid
                true,
                LocalDateTime.now(),
                mockParent
        );

        assertNull(method);
        System.out.println("The last four digits is invalid: " + method);
    }

    @Test
    void createPaymentMethod_nonCard_noBrandRequired() {

        PaymentMethod method = PaymentMethodFactory.createPaymentMethod(
                PaymentMethod.MethodType.PAYPAL,
                "PayPal",
                "paypal_token_123456789012345",
                null,
                null,
                true,
                LocalDateTime.now(),
                mockParent
        );

        assertNotNull(method);
        assertEquals(PaymentMethod.MethodType.PAYPAL, method.getMethodType());
        System.out.println("Non-card PaymentMethod (PayPal) created successfully without card details. \n" + method);
    }

    @Test
    void createPaymentMethod_futureCreatedAt() {

        PaymentMethod method = PaymentMethodFactory.createPaymentMethod(
                PaymentMethod.MethodType.CARD,
                "Stripe",
                "tok_12345678901234567890",
                "Visa",
                "1234",
                true,
                LocalDateTime.now().plusDays(1), // future.
                mockParent
        );

        assertNull(method);
        System.out.println("The createdAt date is invalid it cannot be in the future: " + method);
    }
}