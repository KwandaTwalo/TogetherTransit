package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IRoleService extends IService<Role, Long> {
    List<Role> getAllRoles();

    Role getByRoleName(Role.RoleName roleName);
}
