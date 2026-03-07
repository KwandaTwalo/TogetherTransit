package com.kwndtwalo.TogetherTransit.service.payment;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.payment.Payment;
import com.kwndtwalo.TogetherTransit.domain.payment.PaymentMethod;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.factory.auth.AuthenticationFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.AddressFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;
import com.kwndtwalo.TogetherTransit.factory.payment.PaymentFactory;
import com.kwndtwalo.TogetherTransit.factory.payment.PaymentMethodFactory;
import com.kwndtwalo.TogetherTransit.factory.users.ParentFactory;
import com.kwndtwalo.TogetherTransit.service.users.ParentService;
import com.kwndtwalo.TogetherTransit.service.users.RoleService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class PaymentServiceTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    private RoleService roleService;

    @Autowired
    ParentService parentService;

    private static Payment savedPayment;

    @Test
    void a_create() {

        Address address = AddressFactory.createAddress(
                "15",
                "Main Street",
                "Claremont",
                "Cape Town",
                7708
        );

        Contact contact = ContactFactory.createContact(
                "0879833308",
                "0769822345"
        );

        Authentication authentication = AuthenticationFactory.createAuthentication(
                "parent@gmail.com",
                "StrongPassword123!",
                LocalDateTime.now(),
                false
        );

        Role role = roleService.getByRoleName(Role.RoleName.PARENT);

        Parent parent = ParentFactory.createParent(
                "Aphiwe",
                "Twalo",
                LocalDate.now(),
                User.AccountStatus.ACTIVE,
                contact,
                address,
                authentication,
                role
        );

        Parent savedParent = parentService.create(parent);
        assertNotNull(savedParent);

        PaymentMethod method = PaymentMethodFactory.createPaymentMethod(
                PaymentMethod.MethodType.CARD,
                "Stripe",
                "tok_12345678901234567891",
                "Visa",
                "1234",
                true,
                LocalDateTime.now(),
                savedParent
        );

        Payment payment = PaymentFactory.createPayment(
                1200.00,
                Payment.CurrencyType.ZAR,
                LocalDateTime.now(),
                LocalDateTime.now().withDayOfMonth(1),
                LocalDateTime.now().withDayOfMonth(30),
                Payment.PaymentStatus.SUCCESS,
                null,
                null,   // booking mocked / seeded in DB
                method
        );

        assertNotNull(payment);

        savedPayment = paymentService.create(payment);
        assertNotNull(savedPayment);
        assertNotNull(savedPayment.getPaymentId());

        System.out.println("Payment created: " + savedPayment);
    }

    @Test
    void b_read() {

        Payment found = paymentService.read(savedPayment.getPaymentId());

        assertNotNull(found);
        assertEquals(savedPayment.getPaymentId(), found.getPaymentId());

        System.out.println("Payment read: " + found);
    }

    @Test
    void c_update() {

        Payment updated = new Payment.Builder()
                .copy(savedPayment)
                .setStatus(Payment.PaymentStatus.REFUNDED)
                .setFailureReason("Parent requested refund")
                .build();

        Payment result = paymentService.update(updated);

        assertNotNull(result);
        assertEquals(Payment.PaymentStatus.REFUNDED, result.getStatus());

        savedPayment = result;

        System.out.println("Payment updated: " + result);
    }

    @Test
    void d_getAllPayments() {

        List<Payment> payments = paymentService.getAllPayments();

        assertNotNull(payments);
        assertFalse(payments.isEmpty());

        payments.forEach(System.out::println);
    }

    @Test
    void e_delete() {
        boolean deleted = paymentService.delete(savedPayment.getPaymentId());

        assertTrue(deleted);
        System.out.println("Payment deleted: " + savedPayment.getPaymentId());
    }


}