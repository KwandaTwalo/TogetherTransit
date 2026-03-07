package com.kwndtwalo.TogetherTransit.service.route;

import com.kwndtwalo.TogetherTransit.domain.route.ExamSession;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IExamSessionService extends IService<ExamSession, Long> {
    List<ExamSession> getAllExamSessions();
}
