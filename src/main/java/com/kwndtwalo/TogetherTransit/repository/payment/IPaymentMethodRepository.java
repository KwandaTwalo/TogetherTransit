package com.kwndtwalo.TogetherTransit.repository.payment;

import com.kwndtwalo.TogetherTransit.domain.payment.PaymentMethod;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    // ---------------------------------
    // PARENT-RELATED QUERIES
    // ---------------------------------

    // Get all payment methods belonging to a parent
    List<PaymentMethod> findByParent(Parent parent);

    // Get only active payment methods for a parent
    List<PaymentMethod> findByParentAndActiveTrue(Parent parent);

    // Get the most recently added payment method
    Optional<PaymentMethod> findFirstByParentOrderByCreatedAtDesc(Parent parent);


    // ---------------------------------
    // METHOD TYPE & PROVIDER
    // ---------------------------------

    // Find payment methods by type (CARD, PAYPAL, EFT)
    List<PaymentMethod> findByMethodType(PaymentMethod.MethodType methodType);

    // Find payment methods by provider (Stripe, PayFast, PayPal)
    List<PaymentMethod> findByProvider(String provider);


    // ---------------------------------
    // DUPLICATE PREVENTION
    // ---------------------------------

    // Prevent saving the same card twice
    boolean existsByParentAndToken(Parent parent, String token);

    // Prevent duplicate cards by last four digits & brand
    boolean existsByParentAndBrandAndLastFourDigits(
            Parent parent,
            String brand,
            String lastFourDigits
    );


    // ---------------------------------
    // STATUS MANAGEMENT
    // ---------------------------------

    // Fetch inactive (disabled) payment methods
    List<PaymentMethod> findByActiveFalse();

    // Check if a parent has at least one active payment method
    boolean existsByParentAndActiveTrue(Parent parent);
}
