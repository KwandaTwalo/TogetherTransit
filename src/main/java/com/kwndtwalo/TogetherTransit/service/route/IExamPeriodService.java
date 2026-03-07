package com.kwndtwalo.TogetherTransit.service.route;

import com.kwndtwalo.TogetherTransit.domain.route.ExamPeriod;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IExamPeriodService extends IService<ExamPeriod, Long> {
    List<ExamPeriod> getAllExamPeriods();
}
