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

import com.kwndtwalo.TogetherTransit.domain.route.ExamSession;
import com.kwndtwalo.TogetherTransit.dto.route.ExamSessionDTO;
import com.kwndtwalo.TogetherTransit.service.route.ExamSessionService;

@RestController
@RequestMapping("/examSession")
public class ExamSessionController {

    private final ExamSessionService examSessionService;

    @Autowired
    public ExamSessionController(ExamSessionService examSessionService) {
        this.examSessionService = examSessionService;
    }

    /**
     * CREATE EXAM SESSION
     * -------------------------------------------------------
     * Creates a new exam transport session for a route.
     *
     * Business Rules:
     * - Session must not be null
     * - Route and exam period must be provided
     * - Duplicate sessions are not allowed
     * - Overlapping sessions on the same route and exam date are not allowed
     *
     * Example:
     * Create a Mathematics Paper 1 pickup session for
     * 2026-06-10 at 06:30.
     */
    @PostMapping("/create")
    public ResponseEntity<ExamSessionDTO> create(@RequestBody ExamSession examSession) {
        
        ExamSession create = examSessionService.create(examSession);
        if (create == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ExamSessionDTO(create));
    }

    /**
     * READ EXAM SESSION BY ID
     * -------------------------------------------------------
     * Fetches a specific exam session.
     *
     * Example use cases:
     * - Admin reviewing exam transport schedules
     * - Parent checking exam pickup details
     */
    @GetMapping("/read/{Id}")
    public ResponseEntity<ExamSessionDTO> read(@PathVariable Long Id) {
        
        ExamSession read = examSessionService.read(Id);
        if (read == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ExamSessionDTO(read));
    }

    /**
     * UPDATE EXAM SESSION
     * -------------------------------------------------------
     * Updates an existing exam session.
     *
     * Business Rules:
     * - Session must not be null
     * - Session ID must be provided
     * - Session must already exist
     *
     * Example updates:
     * - Change pickup time
     * - Update drop-off time
     * - Modify the paper type
     */
    @PutMapping("/update")
    public ResponseEntity<ExamSessionDTO> update(@RequestBody ExamSession session) {

        ExamSession updatedSession = examSessionService.update(session);

        if (updatedSession == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new ExamSessionDTO(updatedSession));
    }

    /**
     * GET ALL EXAM SESSIONS
     * -------------------------------------------------------
     * Returns all exam sessions in the system.
     *
     * Used for:
     * - Admin dashboards
     * - Route exam planning
     * - Transport scheduling
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<ExamSessionDTO>> getAllExamSessions() {

        List<ExamSessionDTO> sessions = examSessionService.getAllExamSessions()
                .stream()
                .map(ExamSessionDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(sessions);
    }

    /**
     * DELETE EXAM SESSION
     * -------------------------------------------------------
     * Removes an exam session from the system.
     *
     * Business Rules:
     * - Session must exist before deletion
     * - Deletion fails if the ID does not exist
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {

        boolean deleted = examSessionService.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(true);
    }    
}
