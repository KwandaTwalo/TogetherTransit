package com.kwndtwalo.TogetherTransit.controller.payment;

import com.kwndtwalo.TogetherTransit.domain.payment.PaymentMethod;
import com.kwndtwalo.TogetherTransit.dto.payment.PaymentMethodDTO;
import com.kwndtwalo.TogetherTransit.service.payment.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/paymentMethod")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentMethodDTO> createPaymentMethod(@RequestBody PaymentMethod paymentMethod) {

        PaymentMethod createdPaymentMethod = paymentMethodService.create(paymentMethod);

        if (createdPaymentMethod == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(new PaymentMethodDTO(createdPaymentMethod));
    }

    @GetMapping("/read/{Id}")
    public ResponseEntity<PaymentMethodDTO> readPaymentMethod(@PathVariable Long Id) {

        PaymentMethod paymentMethod = paymentMethodService.read(Id);
        if (paymentMethod == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PaymentMethodDTO(paymentMethod));
    }

    @PutMapping("/update")
    public ResponseEntity<PaymentMethodDTO> updatePaymentMethod(@RequestBody PaymentMethod paymentMethod) {

        PaymentMethod createdPaymentMethod = paymentMethodService.update(paymentMethod);
        if (createdPaymentMethod == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(new PaymentMethodDTO(createdPaymentMethod));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PaymentMethodDTO>> getAllPaymentMethods() {

        List<PaymentMethodDTO> paymentMethods = paymentMethodService.getAllPaymentMethods()
                .stream()
                .map(PaymentMethodDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentMethods);
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long Id) {

        boolean deleted = paymentMethodService.delete(Id);
        return ResponseEntity.ok(deleted);
    }
}
