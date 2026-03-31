package com.kwndtwalo.TogetherTransit.controller.route;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kwndtwalo.TogetherTransit.domain.route.ExamPeriod;
import com.kwndtwalo.TogetherTransit.dto.route.ExamPeriodDTO;
import com.kwndtwalo.TogetherTransit.service.route.ExamPeriodService;

@RestController
@RequestMapping("/examPeriod")  
public class ExamPeriodController {

    private final ExamPeriodService examPeriodService;

    @Autowired
    public ExamPeriodController(ExamPeriodService examPeriodService) {
        this.examPeriodService = examPeriodService;
    }


    /**
     * CREATE EXAM PERIOD
     * -------------------------------------------------------
     * Creates a new exam period for a specific route.
     *
     * Business Rules:
     * - Exam period must not be null
     * - Route must exist
     * - Exact duplicates are not allowed
     * - Overlapping exam periods on the same route are not allowed
     *
     * Example:
     * Create an exam transport period for a route from
     * 2026-06-01 to 2026-06-20.
     */
    @PostMapping("/create")
    public ResponseEntity<ExamPeriodDTO> create(@RequestBody ExamPeriod examPeriod) {
        
        ExamPeriod create = examPeriodService.create(examPeriod);
        if (create == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ExamPeriodDTO(create));
    }

    /**
     * READ EXAM PERIOD BY ID
     * -------------------------------------------------------
     * Fetches a specific exam period.
     *
     * Example use cases:
     * - Admin reviewing exam transport dates
     * - Parent checking special exam schedules
     */
    @GetMapping("/read/{Id}")
    public ResponseEntity<ExamPeriodDTO> read(@PathVariable Long Id) {

        ExamPeriod read = examPeriodService.read(Id);

        if (read == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ExamPeriodDTO(read));
    }

    /**
     * UPDATE EXAM PERIOD
     * -------------------------------------------------------
     * Updates an existing exam period.
     *
     * Business Rules:
     * - Exam period must not be null
     * - Exam period ID must be provided
     * - Exam period must already exist
     *
     * Example updates:
     * - Change the exam start date
     * - Extend the exam end date
     */
    @PutMapping("/update")
    public ResponseEntity<ExamPeriodDTO> update(@RequestBody ExamPeriod examPeriod) {
        
        ExamPeriod update = examPeriodService.update(examPeriod);
        if (update == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ExamPeriodDTO(update));
    }

     /**
     * GET ALL EXAM PERIODS
     * -------------------------------------------------------
     * Returns all exam periods in the system.
     *
     * Used for:
     * - Admin dashboards
     * - Route exam planning
     */

     @GetMapping("/getAll")
     public ResponseEntity<List<ExamPeriodDTO>> getAllExamPeriods() {
        
        List<ExamPeriodDTO> allExamPEriod = examPeriodService.getAllExamPeriods()
        .stream()
        .map(ExamPeriodDTO::new)
        .collect(Collectors.toList());
        return ResponseEntity.ok(allExamPEriod);
     }

     /**
     * DELETE EXAM PERIOD
     * -------------------------------------------------------
     * Removes an exam period from the system.
     *
     * Business Rules:
     * - Exam period must exist before deletion
     * - Related exam sessions are removed automatically
     *   because of cascade/orphanRemoval settings
     */
    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long Id) {
        
        boolean delete = examPeriodService.delete(Id);
        if (!delete) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(true);
    }

    
}
