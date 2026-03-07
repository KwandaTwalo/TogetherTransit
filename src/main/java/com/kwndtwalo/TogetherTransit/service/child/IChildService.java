package com.kwndtwalo.TogetherTransit.service.child;

import com.kwndtwalo.TogetherTransit.domain.child.Child;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IChildService extends IService<Child, Long> {
    List<Child> getAllChildren();

    List<Child> getChildrenByParent(Parent parent);

    long countChildrenForParent(Parent parent);
}
