package com.kwndtwalo.TogetherTransit.controller.user;

import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;
import com.kwndtwalo.TogetherTransit.dto.users.PermissionLevelDTO;
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

    /*
     * CREATE Permission Level
     * ------------------------
     * Normally:
     *   This would not be exposed publicly because permission levels are
     *   seeded automatically at startup using PermissionSeeder.
     *
     * Why still keep this method?
     * - For testing
     * - For admin-only management endpoints
     *
     * This endpoint:
     * - Accepts a PermissionLevel entity
     * - Calls service.create()
     * - Returns a DTO response
     */
    @PostMapping("/create")
    public ResponseEntity<PermissionLevelDTO> create(@RequestBody PermissionLevel permissionLevel) {

        PermissionLevel created = permissionLevelService.create(permissionLevel);

        if (created == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new PermissionLevelDTO(created));
    }

    /*
     * READ Permission Level BY ID
     * ----------------------------
     * Used to:
     * - Fetch one permission level by its database ID.
     */
    @GetMapping("/read/{id}")
    public ResponseEntity<PermissionLevelDTO> read(@PathVariable Long id) {

        PermissionLevel permission = permissionLevelService.read(id);

        if (permission == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new PermissionLevelDTO(permission));
    }

    /*
     * GET ALL Permission Levels
     * --------------------------
     * Used when:
     * - Admin UI needs to list all permission levels
     * - System needs to display or assign permission levels
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<PermissionLevelDTO>> getAll() {

        List<PermissionLevelDTO> dtoList = permissionLevelService
                .getAllPermissionLevels()
                .stream()
                .map(PermissionLevelDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    /*
     * GET Permission Level BY TYPE
     * ------------------------------
     * Used when:
     * - You want to fetch permission based on business meaning
     *   instead of database ID.
     *
     * Example:
     * GET /api/permission-levels/type/SUPER_ADMIN
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<PermissionLevelDTO> getByType(
            @PathVariable PermissionLevel.PermissionType type) {

        PermissionLevel permission =
                permissionLevelService.getByPermissionLevelType(type);

        if (permission == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new PermissionLevelDTO(permission));
    }

    /*
     * UPDATE Permission Level
     * ------------------------
     * Used to:
     * - Modify description or allowed actions
     *
     * Important:
     * - The permission ID MUST already exist.
     */
    @PutMapping("/update")
    public ResponseEntity<PermissionLevelDTO> update(
            @RequestBody PermissionLevel permissionLevel) {

        PermissionLevel updated =
                permissionLevelService.update(permissionLevel);

        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new PermissionLevelDTO(updated));
    }

    /*
     * DELETE Permission Level
     * ------------------------
     * Usually:
     * - This endpoint should be VERY restricted.
     * - Permission levels are core system data.
     *
     * Example:
     * DELETE /api/permission-levels/delete/3
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        boolean deleted = permissionLevelService.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Permission level deleted successfully");
    }
}