package com.kwndtwalo.TogetherTransit.factory.users;

import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;
import com.kwndtwalo.TogetherTransit.util.Helper;

import java.util.Set;

public class PermissionLevelFactory {

    public static PermissionLevel createPermissionLevel(
            PermissionLevel.PermissionType permissionType,
            String permissionDescription,
            Set<String> allowedActions
    ) {

        if (!Helper.isValidPermissionType(permissionType)) {
            throw new IllegalArgumentException("Permission type is required");
        }

        if (!Helper.isValidPermissionDescription(permissionDescription)) {
            throw new IllegalArgumentException("Invalid permission description");
        }

        if (!Helper.isValidActionSet(allowedActions)) {
            throw new IllegalArgumentException("Invalid or empty allowed actions");
        }

        return new PermissionLevel.Builder()
                .setPermissionType(permissionType)
                .setPermissionDescription(permissionDescription)
                .setAllowedActions(allowedActions)
                .build();
    }
}
