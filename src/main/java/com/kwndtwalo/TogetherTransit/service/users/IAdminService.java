package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.users.Admin;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IAdminService extends IService<Admin, Long> {
    List<Admin> getAllAdmins();
}
