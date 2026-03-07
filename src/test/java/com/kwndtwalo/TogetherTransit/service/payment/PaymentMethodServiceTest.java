package com.kwndtwalo.TogetherTransit.service.payment;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.payment.PaymentMethod;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.factory.auth.AuthenticationFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.AddressFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;
import com.kwndtwalo.TogetherTransit.factory.payment.PaymentMethodFactory;
import com.kwndtwalo.TogetherTransit.factory.users.ParentFactory;
import com.kwndtwalo.TogetherTransit.service.users.ParentService;
import com.kwndtwalo.TogetherTransit.service.users.RoleService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class PaymentMethodServiceTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    ParentService parentService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    private static PaymentMethod savedMethod;

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

        assertNotNull(method);

        savedMethod = paymentMethodService.create(method);
        assertNotNull(savedMethod);
        assertNotNull(savedMethod.getPaymentMethodId());

        System.out.println("Payment method created: " + savedMethod);
    }

    @Test
    void b_read() {

        PaymentMethod found = paymentMethodService.read(savedMethod.getPaymentMethodId());
        assertNotNull(found);
        assertEquals(savedMethod.getPaymentMethodId(), found.getPaymentMethodId());
        System.out.println("Payment method read successfully: " + found);
    }

    @Test
    void c_update() {
        PaymentMethod updated = new PaymentMethod.Builder()
                .copy(savedMethod)
                .setLastFourDigits("4321")
                .setActive(false)
                .build();

        PaymentMethod result = paymentMethodService.update(updated);
        assertNotNull(result);
        assertEquals("4321", result.getLastFourDigits());
        assertFalse(result.getActive());

        System.out.println("Payment method update successfully: " + result);
    }

    @Test
    void d_getAllPaymentMethods() {
        List<PaymentMethod> methods = paymentMethodService.getAllPaymentMethods();
        assertNotNull(methods);
        assertFalse(methods.isEmpty());
        methods.forEach(System.out::println);
    }

    @Test
    void e_delete() {
        boolean deleted = paymentMethodService.delete(savedMethod.getPaymentMethodId());
        assertTrue(deleted);
        System.out.println("Payment method disabled successfully: " + savedMethod.getPaymentMethodId());
    }
}