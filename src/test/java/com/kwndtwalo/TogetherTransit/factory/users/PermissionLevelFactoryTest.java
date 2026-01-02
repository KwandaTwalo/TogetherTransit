package com.kwndtwalo.TogetherTransit.factory.users;

import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PermissionLevelFactoryTest {

    /* =========================
       POSITIVE TEST CASE
       ========================= */

    @Test
    void createPermissionLevel_validInput_shouldCreatePermission() {

        PermissionLevel permission = PermissionLevelFactory.createPermissionLevel(
                PermissionLevel.PermissionType.SUPER_ADMIN,
                "Full system access and control",
                Set.of("ALL")
        );

        assertNotNull(permission);
        assertEquals(PermissionLevel.PermissionType.SUPER_ADMIN, permission.getPermissionType());
        assertEquals("Full system access and control", permission.getPermissionDescription());
        assertTrue(permission.getAllowedActions().contains("ALL"));
        System.out.println("Permission level: " + permission);
    }

    /* =========================
       NEGATIVE TEST CASES
       ========================= */

    @Test
    void createPermissionLevel_nullPermissionType_shouldThrowException() {

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PermissionLevelFactory.createPermissionLevel(
                        null,
                        "Valid description",
                        Set.of("VIEW_USERS")
                )
        );

        assertEquals("Permission type is required", exception.getMessage());
        System.out.println("Permission type is required");
    }

    @Test
    void createPermissionLevel_emptyDescription_shouldThrowException() {

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PermissionLevelFactory.createPermissionLevel(
                        PermissionLevel.PermissionType.SUPPORT_ADMIN,
                        "",
                        Set.of("VIEW_SUPPORT_TICKETS")
                )
        );

        assertEquals("Invalid permission description", exception.getMessage());
        System.out.println("Invalid permission description");
    }

    @Test
    void createPermissionLevel_shortDescription_shouldThrowException() {

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PermissionLevelFactory.createPermissionLevel(
                        PermissionLevel.PermissionType.FINANCE_ADMIN,
                        "Too short",
                        Set.of("VIEW_PAYMENTS")
                )
        );

        assertEquals("Invalid permission description", exception.getMessage());
        System.out.println("Invalid permission description");
    }

    @Test
    void createPermissionLevel_nullActions_shouldThrowException() {

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PermissionLevelFactory.createPermissionLevel(
                        PermissionLevel.PermissionType.VERIFICATION_ADMIN,
                        "Handles account verification",
                        null
                )
        );

        assertEquals("Invalid or empty allowed actions", exception.getMessage());
        System.out.println("Invalid or empty allowed actions");
    }

    @Test
    void createPermissionLevel_emptyActions_shouldThrowException() {

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PermissionLevelFactory.createPermissionLevel(
                        PermissionLevel.PermissionType.VERIFICATION_ADMIN,
                        "Handles account verification",
                        Set.of()
                )
        );

        assertEquals("Invalid or empty allowed actions", exception.getMessage());
        System.out.println("Invalid or empty allowed actions");
    }

    @Test
    void createPermissionLevel_invalidActionFormat_shouldThrowException() {

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PermissionLevelFactory.createPermissionLevel(
                        PermissionLevel.PermissionType.SUPPORT_ADMIN,
                        "Handles customer support",
                        Set.of("view tickets") // invalid (lowercase + space)
                )
        );

        assertEquals("Invalid or empty allowed actions", exception.getMessage());
        System.out.println("Invalid or empty allowed actions");
    }
}
