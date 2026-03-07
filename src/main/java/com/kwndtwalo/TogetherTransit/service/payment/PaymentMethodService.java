package com.kwndtwalo.TogetherTransit.service.payment;

import com.kwndtwalo.TogetherTransit.domain.payment.PaymentMethod;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.repository.payment.IPaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService implements IPaymentMethodService {

    private IPaymentMethodRepository paymentMethodRepository;

    @Autowired
    public PaymentMethodService(IPaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    /*
     * Business rules:
     * - Parent must exist
     * - Prevent duplicate cards (token OR brand + last 4 digits)
     * - If this is the first card, auto-activate it
     */
    @Override
    public PaymentMethod create(PaymentMethod paymentMethod) {
        //ensuring that the method must belong to a parent. Or parent must exist.
        if (paymentMethod == null || paymentMethod.getParent() == null) {
            return null;
        }

        //Check for duplicate cards (Token)
        boolean tokenExists =
                paymentMethodRepository.existsByParentAndToken(
                        paymentMethod.getParent(),
                        paymentMethod.getToken()
                );
        //Check for duplicate cards (Brad and Last four digits).
        boolean cardExists =
                paymentMethodRepository.existsByParentAndBrandAndLastFourDigits(
                        paymentMethod.getParent(),
                        paymentMethod.getBrand(),
                        paymentMethod.getLastFourDigits()
                );
        //Stop duplicates. (I said || instead of && because sometimes token changes).
        //This helps to not save the same card again.
        if (tokenExists || cardExists) {
            return null;
        }

        //Checks if the parent already has an active card. If parent has no active card, auto-activate this one.
        boolean hasActive =
                paymentMethodRepository.existsByParentAndActiveTrue(
                        paymentMethod.getParent()
                );

        //Auto-activate the first card.
        if (!hasActive) {
            paymentMethod = new PaymentMethod.Builder()
                    .copy(paymentMethod)
                    .setActive(true)
                    .build();
        }
        return paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public PaymentMethod read(Long Id) {
        return paymentMethodRepository.findById(Id).orElse(null);
    }

    /*
     * Business rules:
     * - Payment method must already exist
     * - Used mainly to enable / disable cards
     */
    @Override
    public PaymentMethod update(PaymentMethod paymentMethod) {
        if (paymentMethod == null || paymentMethod.getPaymentMethodId() == null) {
            return null;
        }
        if (!paymentMethodRepository.existsById(paymentMethod.getPaymentMethodId())) {
            return null;
        }
        return paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    /*
     * Soft delete (disable card)
     */
    @Override
    public boolean delete(Long Id) {
        PaymentMethod method = paymentMethodRepository.findById(Id).orElse(null);
        if (method == null) {
            return false;
        }

        PaymentMethod disabled = new PaymentMethod.Builder()
                .copy(method)
                .setActive(false)
                .build();

        paymentMethodRepository.save(disabled);
        return true;
    }

    /* ================= EXTRA BUSINESS METHODS ================= */

    /** All methods for a parent */
    public List<PaymentMethod> getMethodsByParent(Parent parent) {
        return paymentMethodRepository.findByParent(parent);
    }

    /** Active methods only */
    public List<PaymentMethod> getActiveMethodsByParent(Parent parent) {
        return paymentMethodRepository.findByParentAndActiveTrue(parent);
    }

    /** Most recently added method */
    public PaymentMethod getLatestMethodForParent(Parent parent) {
        return paymentMethodRepository
                .findFirstByParentOrderByCreatedAtDesc(parent)
                .orElse(null);
    }

    /** Admin view */
    public List<PaymentMethod> getInactiveMethods() {
        return paymentMethodRepository.findByActiveFalse();
    }
}
