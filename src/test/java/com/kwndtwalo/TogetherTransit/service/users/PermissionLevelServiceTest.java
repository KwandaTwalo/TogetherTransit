package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class PermissionLevelServiceTest {

    @Autowired
    private PermissionLevelService permissionLevelService;

    private static PermissionLevel savedPermission;

    @Test
    void a_create() {
        savedPermission = permissionLevelService.getByPermissionLevelType(PermissionLevel.PermissionType.SUPER_ADMIN);
        assertNotNull(savedPermission);
        assertEquals(PermissionLevel.PermissionType.SUPER_ADMIN, savedPermission.getPermissionType());
        System.out.println("Fetched permission: " + savedPermission);
    }

    @Test
    void b_read() {
        PermissionLevel found = permissionLevelService.read(savedPermission.getPermissionId());
        assertNotNull(found);
        assertEquals(savedPermission.getPermissionId(), found.getPermissionId());
        System.out.println("read permission: " + found);
    }

    @Test
    void c_update() {
        PermissionLevel updated = new PermissionLevel.Builder()
                .copy(savedPermission)
                .setPermissionDescription("Updated full system access")
                .setAllowedActions(Set.of("ALL", "AUDIT_LOGS"))
                .build();
        PermissionLevel result = permissionLevelService.update(updated);
        assertNotNull(result);

        assertTrue(result.getAllowedActions().contains("AUDIT_LOGS"));
        savedPermission = result;
        System.out.println("updated permission: " + result);
    }

    @Test
    void d_getAllPermissionLevels() {
        List<PermissionLevel> permissionLevels = permissionLevelService.getAllPermissionLevels();
        assertNotNull(permissionLevels);
        assertFalse(permissionLevels.isEmpty());

        System.out.println("All permission levels: " );
        for (PermissionLevel permissionLevel : permissionLevels) {
            System.out.println(permissionLevel);
        }
    }

    @Test
    void f_delete() {
        boolean deleted = permissionLevelService.delete(savedPermission.getPermissionId());
        assertTrue(deleted);
        System.out.println("Permission deleted: " + savedPermission.getPermissionId());
    }
}