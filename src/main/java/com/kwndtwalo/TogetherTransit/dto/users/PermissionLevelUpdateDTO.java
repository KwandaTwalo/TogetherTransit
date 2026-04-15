package com.kwndtwalo.TogetherTransit.dto.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

/**
 * PermissionLevelUpdateDTO
 * Purpose: Used ONLY by SUPER_ADMIN to update the definition of a PermissionLevel.
 * Important:
 * - This is different from AdminPermissionDTO (which assigns a permission to an admin).
 * - This DTO changes the permission level itself (description or allowed actions).
 * - Should be restricted to SUPER_ADMIN actions to prevent misuse.
 */
public class PermissionLevelUpdateDTO {

    @NotNull(message = "Permission ID is required")
    private Long permissionId; // identifies which permission level to update

    @NotBlank(message = "Permission description is required")
    private String permissionDescription; // new description for the permission level

    @NotNull(message = "Allowed actions cannot be null")
    private Set<String> allowedActions; // updated set of allowed actions

    public PermissionLevelUpdateDTO() {}

    public PermissionLevelUpdateDTO(Long permissionId, String permissionDescription, Set<String> allowedActions) {
        this.permissionId = permissionId;
        this.permissionDescription = permissionDescription;
        this.allowedActions = allowedActions;
    }

    // GETTERS & SETTERS
    public Long getPermissionId() { return permissionId; }
    public void setPermissionId(Long permissionId) { this.permissionId = permissionId; }

    public String getPermissionDescription() { return permissionDescription; }
    public void setPermissionDescription(String permissionDescription) { this.permissionDescription = permissionDescription; }

    public Set<String> getAllowedActions() { return allowedActions; }
    public void setAllowedActions(Set<String> allowedActions) { this.allowedActions = allowedActions; }
}