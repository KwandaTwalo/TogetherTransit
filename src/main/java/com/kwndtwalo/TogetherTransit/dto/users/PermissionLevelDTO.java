package com.kwndtwalo.TogetherTransit.dto.users;

import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;
import java.util.Set;

/**
 * PermissionLevelDTO
 * Purpose:
 * - Used to expose permission level information in API responses.
 * - Shows the type of permission, its description, and the allowed actions.
 *
 * Important Notes:
 * - This DTO is **read-only**: it should be used for responses, not for creating or updating permissions.
 * - Permissions are seeded and system-controlled (via PermissionSeeder), so clients should not send this DTO in requests.
 * - If you ever allow SUPER_ADMIN to update permission definitions, use a separate DTO (PermissionLevelUpdateDTO).
 */
public class PermissionLevelDTO {

    private Long permissionId;              // Database ID of the permission level
    private String permissionType;          // Enum value converted to string (e.g., "SUPER_ADMIN")
    private String permissionDescription;   // Human-readable description of the permission level
    private Set<String> allowedActions;     // List of actions this permission level grants

    // No-args constructor (needed for frameworks like Jackson)
    public PermissionLevelDTO() {}

    // ENTITY → DTO mapping constructor
    public PermissionLevelDTO(PermissionLevel permissionLevel) {
        this.permissionId = permissionLevel.getPermissionId();
        this.permissionType = permissionLevel.getPermissionType().name();
        this.permissionDescription = permissionLevel.getPermissionDescription();
        this.allowedActions = permissionLevel.getAllowedActions();
    }

    // GETTERS (needed for JSON serialization in REST responses)
    public Long getPermissionId() { return permissionId; }
    public String getPermissionType() { return permissionType; }
    public String getPermissionDescription() { return permissionDescription; }
    public Set<String> getAllowedActions() { return allowedActions; }
}