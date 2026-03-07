package com.kwndtwalo.TogetherTransit.dto.users;

import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;

import java.util.Set;

public class PermissionLevelDTO {

    private Long permissionId;
    private String permissionType;
    private String permissionDescription;
    private Set<String> allowedActions;

    public PermissionLevelDTO(PermissionLevel permissionLevel) {
        this.permissionId = permissionLevel.getPermissionId();
        this.permissionType = permissionLevel.getPermissionType().name();
        this.permissionDescription = permissionLevel.getPermissionDescription();
        this.allowedActions = permissionLevel.getAllowedActions();
    }

    @Override
    public String toString() {
        return "\nPermissionLevelDTO{" +
                "permissionId=" + permissionId +
                ", permissionType='" + permissionType + '\'' +
                ", permissionDescription='" + permissionDescription + '\'' +
                ", allowedActions=" + allowedActions +
                '}';
    }
}