package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.factory.auth.AuthenticationFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.AddressFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;
import com.kwndtwalo.TogetherTransit.factory.users.ParentFactory;
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
class ParentServiceTest {

    @Autowired
    private ParentService parentService;

    @Autowired
    private RoleService roleService;

    private static Parent savedParent;

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

        assertNotNull(parent);
        savedParent = parentService.create(parent);
        assertNotNull(savedParent);
        assertNotNull(savedParent.getUserId());
        System.out.println("Parent created successfully: " + savedParent);
    }

    @Test
    void b_read() {
        Parent found = parentService.read(savedParent.getUserId());
        assertNotNull(found);
        assertEquals(savedParent.getUserId(), found.getUserId());
        System.out.println("Parent read successfully: " + savedParent);
    }

    @Test
    void c_update() {
        Parent updated = new Parent.Builder()
                .copy(savedParent)
                .setLastName("Ndlovu")
                .build();
        Parent result = parentService.update(updated);
        assertNotNull(result);
        assertEquals("Ndlovu", result.getLastName());

        savedParent = result;
        System.out.println("Parent update successfully: " + result);
    }

    @Test
    void d_getAllParents() {
        List<Parent> parents = parentService.getAllParents();
        assertNotNull(parents);
        assertFalse(parents.isEmpty());
        System.out.println("Parent in the system: " + parents);
    }

    @Test
    void e_delete() {
        boolean deleted = parentService.delete(savedParent.getUserId());
        assertTrue(deleted);
        System.out.println("Parent deleted successfully: " + savedParent);
    }
}