package com.kwndtwalo.TogetherTransit.dto.users;

import com.kwndtwalo.TogetherTransit.domain.users.Admin;

/**
 * AdminPermissionDTO
 * Purpose: Used ONLY for changing an admin's permission level.
 * Important: Should be restricted to SUPER_ADMIN actions.
 * Prevents normal admins from modifying their own permission level.
 * Example: “Change Admin #5 from SUPPORT_ADMIN to FINANCE_ADMIN.”
 */
public class AdminPermissionDTO {

    private Long adminId; // identifies the admin to update
    private String currentPermissionType;
    private String newPermissionType; // set by SUPER_ADMIN when changing permissions

    public AdminPermissionDTO() {
        // Default constructor for JSON deserialization
    }

    // ENTITY → DTO mapping
    public AdminPermissionDTO(Admin admin) {
        this.adminId = admin.getUserId();
        this.currentPermissionType = admin.getPermissionLevel() != null
                ? admin.getPermissionLevel().getPermissionType().name()
                : null;
    }

    // GETTERS & SETTERS
    public Long getAdminId() {
        return adminId;
    }

    public String getCurrentPermissionType() {
        return currentPermissionType;
    }

    public String getNewPermissionType() {
        return newPermissionType;
    }

    public void setNewPermissionType(String newPermissionType) {
        this.newPermissionType = newPermissionType;
    }

}