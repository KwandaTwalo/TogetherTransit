package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IPermissionLevelService extends IService<PermissionLevel, Long> {

    List<PermissionLevel> getAllPermissionLevels();

    PermissionLevel getByPermissionLevelType(PermissionLevel.PermissionType permissionLevelType);
}
