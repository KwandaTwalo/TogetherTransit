package com.kwndtwalo.TogetherTransit.service.school;

import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface ISchoolService extends IService<School, Long> {
    List<School> getAllSchools();
}
