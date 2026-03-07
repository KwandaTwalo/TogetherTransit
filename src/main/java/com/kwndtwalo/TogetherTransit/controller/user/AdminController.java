package com.kwndtwalo.TogetherTransit.controller.user;

import com.kwndtwalo.TogetherTransit.domain.users.Admin;
import com.kwndtwalo.TogetherTransit.dto.users.AdminDTO;
import com.kwndtwalo.TogetherTransit.service.users.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /*
     * CREATE
     * ---------------------------------------------------
     * Purpose:
     * Creates a new Admin account or returns an existing one.
     *
     * Business Rule:
     * - Prevents duplicate admins using:
     *   firstName + lastName uniqueness check.
     *
     * Returns:
     * - 200 OK → Admin created or already exists
     * - 400 Bad Request → Invalid admin data
     */
    @PostMapping("/create")
    public ResponseEntity<AdminDTO> create(@RequestBody Admin admin) {

        Admin created = adminService.create(admin);

        if (created == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new AdminDTO(created));
    }

    /*
     * READ BY ID
     * ---------------------------------------------------
     * Purpose:
     * Fetches a single Admin record using ID.
     *
     * Returns:
     * - 200 OK → Admin found
     * - 404 Not Found → Admin does not exist
     */
    @GetMapping("/read/{id}")
    public ResponseEntity<AdminDTO> read(@PathVariable Long id) {

        Admin admin = adminService.read(id);

        if (admin == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new AdminDTO(admin));
    }

    /*
     * UPDATE
     * ---------------------------------------------------
     * Purpose:
     * Updates an existing Admin profile.
     *
     * Business Rule:
     * - Admin must already exist.
     *
     * Returns:
     * - 200 OK → Admin updated
     * - 404 Not Found → Admin not found
     */
    @PutMapping("/update")
    public ResponseEntity<AdminDTO> update(@RequestBody Admin admin) {

        Admin updated = adminService.update(admin);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new AdminDTO(updated));
    }

    /*
     * GET ALL ADMINS
     * ---------------------------------------------------
     * Purpose:
     * Retrieves all admins in the system.
     *
     * Returns:
     * - 200 OK → List of admins
     * - 204 No Content → No admins found
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {

        List<Admin> admins = adminService.getAllAdmins();

        if (admins == null || admins.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<AdminDTO> dtoList = admins.stream()
                .map(AdminDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    /*
     * DELETE
     * ---------------------------------------------------
     * Purpose:
     * Deletes an Admin by ID.
     *
     * Business Rule:
     * - Only deletes if admin exists.
     *
     * Returns:
     * - 200 OK → Deleted successfully
     * - 404 Not Found → Admin does not exist
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        boolean deleted = adminService.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
}