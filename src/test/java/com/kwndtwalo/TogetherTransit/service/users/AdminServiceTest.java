package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.users.Admin;
import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.factory.auth.AuthenticationFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.AddressFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;
import com.kwndtwalo.TogetherTransit.factory.users.AdminFactory;
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
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    PermissionLevelService permissionLevelService;

    private static Admin savedAdmin;

    @Test
    void a_create() { /** FINISH THIS METHOD*/
        // Address
        Address address = AddressFactory.createAddress(
                "42",
                "Oak Avenue",
                "Rondebosch",
                "Cape Town",
                7700
        );

        // Contact
        Contact contact = ContactFactory.createContact(
                "0215558899",
                "0791234567"
        );

        // Authentication
        Authentication authentication = AuthenticationFactory.createAuthentication(
                "admin@togethertransit.co.za",
                "AdminStrongPass!123",
                LocalDateTime.now().minusDays(1),
                false
        );

        // Role (PRELOADED role from DB recommended)
        Role role = roleService.getByRoleName(Role.RoleName.ADMIN);
        PermissionLevel permissionLevel =
                permissionLevelService.getByPermissionLevelType(PermissionLevel.PermissionType.SUPER_ADMIN);

        // Admin
        Admin admin = AdminFactory.createAdmin(
                LocalDateTime.now(),                 // lastLogin
                "Thinam",                            // firstName
                "Twalo",                           // lastName
                LocalDate.now().minusMonths(6),      // createdAt
                User.AccountStatus.ACTIVE,            // accountStatus
                contact,
                address,
                authentication,
                role,
                permissionLevel        // permissionLevel
        );
        assertNotNull(admin);
        savedAdmin = adminService.create(admin);
        assertNotNull(savedAdmin);
        assertNotNull(savedAdmin.getUserId());
        System.out.println("Admin created successfully: " + savedAdmin);

    }

    @Test
    void b_read() {
        Admin found = adminService.read(savedAdmin.getUserId());
        assertNotNull(found);
        assertEquals(savedAdmin.getUserId(), found.getUserId());
        System.out.println("Admin read successfully: " + found);
    }

    @Test
    void c_update() {
        Admin updated = new Admin.Builder()
                .copy(savedAdmin)
                .setLastName("Fafaza")
                .build();
        Admin result = adminService.update(updated);
        assertNotNull(result);
        assertEquals("Fafaza", result.getLastName());

        savedAdmin = result;
        System.out.println("Admin updated successfully: " + result);
    }

    @Test
    void d_getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        assertNotNull(admins);
        assertFalse(admins.isEmpty());
        System.out.println("Admin in the system: " + admins);
    }

    @Test
    void e_delete() {
        boolean deleted = adminService.delete(savedAdmin.getUserId());
        assertTrue(deleted);
        System.out.println("Admin deleted successfully: " + savedAdmin);
    }
}