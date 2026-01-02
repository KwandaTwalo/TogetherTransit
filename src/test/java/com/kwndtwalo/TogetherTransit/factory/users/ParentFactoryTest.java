package com.kwndtwalo.TogetherTransit.factory.users;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ParentFactoryTest {

    private Authentication authentication = Mockito.mock(Authentication.class);
    private Role role = Mockito.mock(Role.class);
    private Address address = Mockito.mock(Address.class);
    private Contact contact = Mockito.mock(Contact.class);

    private Parent parent1 = ParentFactory.createParent("Nokuzola", "Ralane",
            LocalDate.now(), User.AccountStatus.ACTIVE, contact, address, authentication, role);

    @Test
    void createParent() {
        assertNotNull(parent1);
        System.out.println("Parent was created successfully: " + parent1);
    }

    @Test
    void testEmptyFields() {
        Parent parent2 = ParentFactory.createParent("", "Ralane",
                LocalDate.now(), User.AccountStatus.ACTIVE, contact, address, authentication, role);
        assertNull(parent2);
        System.out.println("Fill in the empty fields: " + parent2);
    }


}