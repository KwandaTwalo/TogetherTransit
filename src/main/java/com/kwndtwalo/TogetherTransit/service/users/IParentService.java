package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IParentService extends IService<Parent, Long> {
    List<Parent> getAllParents();
}
