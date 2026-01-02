package com.kwndtwalo.TogetherTransit.factory.users;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.users.Admin;
import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AdminFactoryTest {

    private Authentication authentication = Mockito.mock(Authentication.class);
    private Role role = Mockito.mock(Role.class);
    private Address address = Mockito.mock(Address.class);
    private Contact contact = Mockito.mock(Contact.class);
    private PermissionLevel permissionLevel = Mockito.mock(PermissionLevel.class);

    private Admin admin1 = AdminFactory.createAdmin(LocalDateTime.now(), "Sandile", "Sibiya",
            LocalDate.now(), User.AccountStatus.REJECTED, contact, address, authentication, role, permissionLevel);

    @Test
    void createAdmin() {
        assertNotNull(admin1);
        System.out.println("Admin created successfully: " + admin1);
    }
}