package com.kwndtwalo.TogetherTransit.controller.user;

import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;
import com.kwndtwalo.TogetherTransit.dto.users.PermissionLevelDTO;
import com.kwndtwalo.TogetherTransit.dto.users.PermissionLevelUpdateDTO;
import com.kwndtwalo.TogetherTransit.service.users.PermissionLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/permissionLevel")
public class PermissionLevelController {

    private final PermissionLevelService permissionLevelService;

    @Autowired
    public PermissionLevelController(PermissionLevelService permissionLevelService) {
        this.permissionLevelService = permissionLevelService;
    }

    // =========================================================
    // CREATE Permission Level
    // - Normally restricted (seeded at startup)
    // - Kept here for testing or SUPER_ADMIN only
    // =========================================================
    @PostMapping("/create")
    public ResponseEntity<PermissionLevelDTO> create(@RequestBody PermissionLevel permissionLevel) {
        try {
            PermissionLevel created = permissionLevelService.create(permissionLevel);
            if (created == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(new PermissionLevelDTO(created));
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(403).build(); // forbidden
        }
    }

    // =========================================================
    // READ Permission Level BY ID
    // =========================================================
    @GetMapping("/read/{id}")
    public ResponseEntity<PermissionLevelDTO> read(@PathVariable Long id) {
        PermissionLevel permission = permissionLevelService.read(id);
        if (permission == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PermissionLevelDTO(permission));
    }

    // =========================================================
    // GET ALL Permission Levels
    // =========================================================
    @GetMapping("/getAllPermissionLevels")
    public ResponseEntity<List<PermissionLevelDTO>> getAll() {
        List<PermissionLevelDTO> dtoList = permissionLevelService
                .getAllPermissionLevels()
                .stream()
                .map(PermissionLevelDTO::new)
                .collect(Collectors.toList());

        if (dtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dtoList);
    }

    // =========================================================
    // GET Permission Level BY TYPE
    // =========================================================
    @GetMapping("/type/{type}")
    public ResponseEntity<PermissionLevelDTO> getByType(
            @PathVariable PermissionLevel.PermissionType type) {

        PermissionLevel permission = permissionLevelService.getByPermissionLevelType(type);
        if (permission == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PermissionLevelDTO(permission));
    }

    // =========================================================
    // UPDATE Permission Level
    // - Accepts PermissionLevelUpdateDTO
    // - Only description and allowed actions can change
    // =========================================================
    @PutMapping("/update/{id}")
    public ResponseEntity<PermissionLevelDTO> update(
            @PathVariable Long id,
            @RequestBody PermissionLevelUpdateDTO dto) {

        PermissionLevel existing = permissionLevelService.read(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        // Build updated entity using Builder pattern
        PermissionLevel updatedEntity = new PermissionLevel.Builder()
                .copy(existing)
                .setPermissionDescription(dto.getPermissionDescription())
                .setAllowedActions(
                        dto.getAllowedActions() != null ? dto.getAllowedActions() : existing.getAllowedActions())
//If allowedActions is null in the DTO, we keep the existing allowed actions unchanged. Otherwise, we update it with the new set provided in the DTO.
                .build();

        PermissionLevel updated = permissionLevelService.update(updatedEntity);
        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new PermissionLevelDTO(updated));
    }

    // =========================================================
    // DELETE Permission Level
    // - Normally restricted (seeded data)
    // =========================================================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            boolean deleted = permissionLevelService.delete(id);
            if (!deleted) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok("Permission level deleted successfully");
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(403).body("Deletion restricted to SUPER_ADMIN");
        }
    }
}