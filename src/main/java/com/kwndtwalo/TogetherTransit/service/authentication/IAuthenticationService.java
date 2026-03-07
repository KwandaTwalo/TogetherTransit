package com.kwndtwalo.TogetherTransit.service.authentication;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IAuthenticationService extends IService<Authentication, Long> {
    List<Authentication> getAuthentications();
}
