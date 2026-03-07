package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.repository.users.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    private IRoleRepository roleRepository;

    @Autowired
    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /*
     * Create a role.
     * Business rule:
     * - A role with the same RoleName must not be duplicated.
     * - If it already exists, return the existing role.
     */
    @Override
    public Role create(Role role) {
        if (role == null || role.getRoleName() == null) {
            return null;
        }
        return roleRepository
                .findByRoleName(role.getRoleName())
                .stream()
                .findFirst()
                .orElseGet(() -> roleRepository.save(role));
    }

    @Override
    public Role read(Long Id) {
        return roleRepository.findById(Id).orElse(null);
    }

    @Override
    public Role update(Role role) {
        if (role == null || role.getRoleId() == null) {
            return null;
        }

        if (!roleRepository.existsById(role.getRoleId())) {
            return null;
        }
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public boolean delete(Long Id) {
        if (!roleRepository.existsById(Id)) {
            return false;
        }
        roleRepository.deleteById(Id);
        return true;
    }

    @Override
    public Role getByRoleName(Role.RoleName roleName) {
        return roleRepository.findByRoleName(roleName)
                .stream()
                .findFirst()
                .orElse(null);
    }
}
