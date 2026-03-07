package com.kwndtwalo.TogetherTransit.service.route;

import com.kwndtwalo.TogetherTransit.domain.route.ExamPeriod;
import com.kwndtwalo.TogetherTransit.repository.route.IExamPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamPeriodService implements IExamPeriodService {

    private final IExamPeriodRepository repository;

    @Autowired
    public ExamPeriodService(IExamPeriodRepository repository) {
        this.repository = repository;
    }

    /*
     * Rules:
     * - Route must exist
     * - Prevent duplicates
     * - Prevent overlapping exam periods on same route
     */
    @Override
    public ExamPeriod create(ExamPeriod examPeriod) {

        if (examPeriod == null || examPeriod.getRoute() == null) {
            return null;
        }

        // Prevent exact duplicates
        if (repository.findByRouteAndStartDateAndEndDate(
                examPeriod.getRoute(),
                examPeriod.getStartDate(),
                examPeriod.getEndDate()
        ).isPresent()) {
            return null;
        }

        // Prevent overlapping exam periods
        boolean overlaps = !repository
                .findByRouteAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        examPeriod.getRoute(),
                        examPeriod.getEndDate(),
                        examPeriod.getStartDate()
                ).isEmpty();

        if (overlaps) {
            return null;
        }

        return repository.save(examPeriod);
    }

    @Override
    public ExamPeriod read(Long id) {
        return repository.findById(id).orElse(null);
    }

    /*
     * Rule:
     * - Exam period must already exist
     */
    @Override
    public ExamPeriod update(ExamPeriod examPeriod) {

        if (examPeriod == null || examPeriod.getExamPeriodId() == null) {
            return null;
        }

        if (!repository.existsById(examPeriod.getExamPeriodId())) {
            return null;
        }

        return repository.save(examPeriod);
    }

    @Override
    public List<ExamPeriod> getAllExamPeriods() {
        return repository.findAll();
    }

    /*
     * DELETE
     * Cascades to ExamSession because of orphanRemoval = true
     */
    @Override
    public boolean delete(Long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}
