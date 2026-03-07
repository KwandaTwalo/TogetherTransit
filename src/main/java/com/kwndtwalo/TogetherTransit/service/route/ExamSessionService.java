package com.kwndtwalo.TogetherTransit.service.route;

import com.kwndtwalo.TogetherTransit.domain.route.ExamSession;
import com.kwndtwalo.TogetherTransit.repository.route.IExamSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamSessionService implements IExamSessionService {

    private IExamSessionRepository repository;

    @Autowired
    public ExamSessionService(IExamSessionRepository repository) {
        this.repository = repository;
    }

    /*
     * Business rules:
     * - Route & exam period must exist
     * - Prevent duplicate sessions
     * - Prevent overlapping sessions on same route & date
     */
    @Override
    public ExamSession create(ExamSession session) {

        if (session == null || session.getRoute() == null || session.getExamPeriod() == null) {
            return null;
        }

        // Prevent duplicate sessions.
        if (repository.findByRouteAndExamDateAndPickupTimeAndPaperType(
                session.getRoute(),
                session.getExamDate(),
                session.getPickupTime(),
                session.getPaperType()
        ).isPresent()) {
            return null;
        }

        //Prevent overlapping sessions.
        boolean overlaps = !repository
                .findByRouteAndExamDateAndPickupTimeLessThanAndDropOffTimeGreaterThan(
                        session.getRoute(),
                        session.getExamDate(),
                        session.getDropOffTime(),
                        session.getPickupTime()
                ).isEmpty();

        if (overlaps) {
            return null;
        }
        return repository.save(session);
    }

    @Override
    public ExamSession read(Long Id) {
        return repository.findById(Id).orElse(null);
    }

    /*
     * UPDATE
     * Business rule:
     * - Session must already exist
     */
    @Override
    public ExamSession update(ExamSession session) {
        if (session == null || session.getExamSessionId() == null) {
            return null;
        }

        if (!repository.existsById(session.getExamSessionId())) {
            return null;
        }

        return repository.save(session);
    }

    @Override
    public List<ExamSession> getAllExamSessions() {
        return repository.findAll();
    }

    @Override
    public boolean delete(Long Id) {
        if (!repository.existsById(Id)) {
            return false;
        }

        repository.deleteById(Id);
        return true;
    }
}
