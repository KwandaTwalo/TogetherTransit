package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.factory.users.RoleFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    private static Role savedRole;

    @Test
    void a_create() {
        Role role = RoleFactory.createRole(Role.RoleName.DRIVER);
        savedRole = roleService.create(role);

        assertNotNull(savedRole);
        assertNotNull(savedRole.getRoleId());

        System.out.println("Created Role: " + savedRole);
    }

    @Test
    void b_preventDuplicateRole() {
        Role duplicate = RoleFactory.createRole(Role.RoleName.DRIVER);
        Role result = roleService.create(duplicate);

        assertEquals(savedRole.getRoleId(), result.getRoleId());

        System.out.println("Duplicate role prevented, existing role reused");
    }

    @Test
    void b_read() {
        Role found = roleService.read(savedRole.getRoleId());

        assertNotNull(found);
        assertEquals(Role.RoleName.DRIVER, found.getRoleName());

        System.out.println("Read Role: " + found);
    }

    @Test
    void c_update() {
        Role updated = new Role.Builder()
                .copy(savedRole)
                .setRoleName(Role.RoleName.ADMIN)
                .build();

        Role result = roleService.update(updated);

        assertNotNull(result);
        assertEquals(Role.RoleName.ADMIN, result.getRoleName());

        System.out.println("Updated Role: " + result);
    }

    @Test
    void d_getAllRoles() {
        List<Role> roles = roleService.getAllRoles();

        assertFalse(roles.isEmpty());

        System.out.println("Total roles in system: " + roles);
    }

    @Test
    void e_delete() {
        boolean deleted = roleService.delete(savedRole.getRoleId());

        assertTrue(deleted);

        System.out.println("Role deleted successfully");
    }
}