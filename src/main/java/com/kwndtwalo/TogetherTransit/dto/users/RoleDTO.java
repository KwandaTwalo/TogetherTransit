package com.kwndtwalo.TogetherTransit.dto.users;

import com.kwndtwalo.TogetherTransit.domain.users.Role;

public class RoleDTO {

    private Long roleId;
    private String roleName;

    public RoleDTO(Role role) {
        this.roleId = role.getRoleId();
        this.roleName = role.getRoleName().name();
    }

    @Override
    public String toString() {
        return "\nRoleDTO{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}