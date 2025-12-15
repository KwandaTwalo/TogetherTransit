package com.kwndtwalo.TogetherTransit.domain.users;

import jakarta.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public enum RoleName {
        PARENT,
        DRIVER,
        ADMIN,
    }

    protected Role() {
    }

    private Role(Builder builder) {
        this.roleName = builder.roleName;
        this.roleId = builder.roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return roleId != null && roleId.equals(role.roleId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + getRoleId() +
                ", roleName='" + getRoleName() + '\'' +
                '}';
    }

    public static class Builder {
        private Long roleId;
        private RoleName roleName;

        public Builder setRoleId(Long roleId) {
            this.roleId = roleId;
            return this;
        }
        public Builder setRoleName(RoleName roleName) {
            this.roleName = roleName;
            return this;
        }

        public Builder copy(Role role) {
            this.roleId = role.getRoleId();
            this.roleName = role.getRoleName();
            return this;
        }

        public Role build() {return new Role(this);}
    }
}
