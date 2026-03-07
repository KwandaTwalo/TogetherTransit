package com.kwndtwalo.TogetherTransit.dto.users;

import com.kwndtwalo.TogetherTransit.domain.users.Admin;

import java.time.LocalDateTime;

public class AdminDTO extends UserDTO {

    private LocalDateTime lastLogin;
    private String permissionType;

    public AdminDTO(Admin admin) {
        super(admin);
        this.lastLogin = admin.getLastLogin();
        this.permissionType = admin.getPermissionLevel() != null
                ? admin.getPermissionLevel().getPermissionType().name()
                : null;
    }

    @Override
    public String toString() {
        return "\nAdminDTO{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                ", accountStatus='" + accountStatus + '\'' +
                ", role='" + role + '\'' +
                ", lastLogin=" + lastLogin +
                ", permissionType='" + permissionType + '\'' +
                '}';
    }
}