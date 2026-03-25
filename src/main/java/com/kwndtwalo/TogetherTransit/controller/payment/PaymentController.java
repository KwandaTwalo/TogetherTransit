package com.kwndtwalo.TogetherTransit.controller.payment;

import com.kwndtwalo.TogetherTransit.domain.payment.Payment;
import com.kwndtwalo.TogetherTransit.dto.payment.PaymentDTO;
import com.kwndtwalo.TogetherTransit.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentDTO> create(@RequestBody Payment payment) {

        Payment created = paymentService.create(payment);

        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new PaymentDTO(created));
    }

    @GetMapping("/read/{Id}")
    public ResponseEntity<PaymentDTO> read(@PathVariable Long Id) {

        Payment payment = paymentService.read(Id);
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PaymentDTO(payment));
    }

    @PutMapping("/update")
    public ResponseEntity<PaymentDTO> update(@RequestBody Payment payment) {

        Payment updated = paymentService.update(payment);
        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new PaymentDTO(updated));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PaymentDTO>> getAll() {

        List<PaymentDTO> paymentList = paymentService.getAllPayments()
                .stream()
                .map(PaymentDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(paymentList);
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long Id) {

        boolean deleted = paymentService.delete(Id);
        if (!deleted) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

}
