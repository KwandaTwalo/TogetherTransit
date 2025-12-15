package com.kwndtwalo.TogetherTransit.domain.users;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class PermissionLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;

    private String permissionDescription;

    /* The below tells hibernate:
    * create a table permission_actions
    * Each row = one allowed action for a specific PermissionLevel.
    * Automatically insert/update/delete actions when saving PermissionLevel.
    */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "permission_actions",
            joinColumns = @JoinColumn(name = "permissionId")
    )
    @Column(name = "action")
    private Set<String> allowedActions = new HashSet<>();

    public enum PermissionType {
        SUPER_ADMIN,
        VERIFICATION_ADMIN,
        SUPPORT_ADMIN,
        FINANCE_ADMIN
    }

    protected PermissionLevel() {}

    private PermissionLevel(Builder builder) {
        this.permissionId = builder.permissionId;
        this.permissionType = builder.permissionType;
        this.permissionDescription = builder.permissionDescription;
        this.allowedActions = builder.allowedActions;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public PermissionType getPermissionType() {
        return permissionType;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public Set<String> getAllowedActions() {
        return allowedActions;
    }

    @Override
    public String toString() {
        return "PermissionLevel{" +
                "\npermissionId=" + getPermissionId() +
                ", \npermissionType=" + getPermissionType() +
                ", \npermissionDescription='" + getPermissionDescription() + '\'' +
                ", \nallowedActions=" + getAllowedActions() +
                '}';
    }

    public static class Builder {
        private Long permissionId;
        private PermissionType permissionType;
        private String permissionDescription;
        private Set<String> allowedActions = new HashSet<>(); // I use this set to store each action.

        public Builder setPermissionId(Long permissionId) {
            this.permissionId = permissionId;
            return this;
        }
        public Builder setPermissionType(PermissionType permissionType) {
            this.permissionType = permissionType;
            return this;
        }
        public Builder setPermissionDescription(String permissionDescription) {
            this.permissionDescription = permissionDescription;
            return this;
        }
        public Builder setAllowedActions(Set<String> allowedActions) {
            this.allowedActions = allowedActions;
            return this;
        }

        public Builder copy(PermissionLevel permissionLevel) {
            this.permissionId = permissionLevel.permissionId;
            this.permissionType = permissionLevel.permissionType;
            this.permissionDescription = permissionLevel.permissionDescription;
            this.allowedActions = permissionLevel.allowedActions;
            return this;
        }

        public PermissionLevel build() {return new PermissionLevel(this);}
    }
}
