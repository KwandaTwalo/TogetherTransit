package com.kwndtwalo.TogetherTransit.factory.users;

import com.kwndtwalo.TogetherTransit.domain.users.Role;

public class RoleFactory {

    public static Role createRole(Role.RoleName roleName) {

        return new Role.Builder()
                .setRoleName(roleName)
                .build();
    }
}
