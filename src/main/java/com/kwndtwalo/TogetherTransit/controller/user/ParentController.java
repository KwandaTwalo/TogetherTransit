package com.kwndtwalo.TogetherTransit.controller.user;

import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.dto.users.ParentDTO;
import com.kwndtwalo.TogetherTransit.service.users.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parent")
public class ParentController {

    private final ParentService parentService;

    @Autowired
    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    /*
     * CREATE
     * -----------------------------------------
     * Purpose:
     * Creates a new parent account or returns existing one.
     *
     * Business Rules:
     * - Prevents duplicate parents using:
     *   firstName + lastName uniqueness check.
     *
     * Returns:
     * - 200 OK → Parent successfully created or already exists
     * - 400 Bad Request → Invalid input data
     */
    @PostMapping("/create")
    public ResponseEntity<ParentDTO> createParent(@RequestBody Parent parent) {
        Parent created = parentService.create(parent);

        if (created == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new ParentDTO(created));
    }

    /*
     * READ BY ID
     * -----------------------------------------
     * Purpose:
     * Retrieves a parent by their unique ID.
     *
     * Returns:
     * - 200 OK → Parent found
     * - 404 Not Found → Parent does not exist
     */
    @GetMapping("/read/{id}")
    public ResponseEntity<ParentDTO> read(@PathVariable long id) {
        Parent parent = parentService.read(id);

        if (parent == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ParentDTO(parent));
    }

    /*
     * UPDATE
     * -----------------------------------------
     * Purpose:
     * Updates an existing parent profile.
     *
     * Business Rules:
     * - Parent must already exist.
     *
     * Returns:
     * - 200 OK → Parent successfully updated
     * - 404 Not Found → Parent does not exist
     */
    @PutMapping("/update")
    public ResponseEntity<ParentDTO> update(@RequestBody Parent parent) {
        Parent updated = parentService.update(parent);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ParentDTO(updated));
    }

    /*
     * GET ALL PARENTS
     * -----------------------------------------
     * Purpose:
     * Fetches all registered parents.
     *
     * Returns:
     * - 200 OK → List of parents
     * - 204 No Content → No parents found
     */
    @GetMapping("/getAllParents")
    public ResponseEntity<List<ParentDTO>> getAllParents() {

        List<Parent> parents = parentService.getAllParents();

        if (parents == null || parents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ParentDTO> parentDTOS = parents.stream()
                .map(ParentDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(parentDTOS);
    }

    /*
     * DELETE
     * -----------------------------------------
     * Purpose:
     * Deletes a parent account using ID.
     *
     * Business Rules:
     * - Only deletes if record exists.
     *
     * Returns:
     * - 200 OK → Deleted successfully
     * - 404 Not Found → Parent does not exist
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {

        boolean deleted = parentService.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
}
