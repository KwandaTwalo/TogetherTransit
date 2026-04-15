package com.kwndtwalo.TogetherTransit.controller.user;

import com.kwndtwalo.TogetherTransit.domain.users.Admin;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.dto.users.AdminDTO;
import com.kwndtwalo.TogetherTransit.dto.users.AdminPermissionDTO;
import com.kwndtwalo.TogetherTransit.dto.users.DriverDTO;
import com.kwndtwalo.TogetherTransit.dto.users.ParentDTO;
import com.kwndtwalo.TogetherTransit.service.users.AdminService;
import com.kwndtwalo.TogetherTransit.service.users.DriverService;
import com.kwndtwalo.TogetherTransit.service.users.ParentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final DriverService driverService;
    private final ParentService parentService;

    @Autowired
    public AdminController(AdminService adminService, DriverService driverService, ParentService parentService) {
        this.adminService = adminService;
        this.driverService = driverService;
        this.parentService = parentService; 
    }

    // =========================================================
    // CREATE ADMIN
    // - Prevents duplicates (firstName + lastName)
    // - Enforces ADMIN role and valid permission level in AdminService
    // =========================================================
    @PostMapping("/create")
    public ResponseEntity<AdminDTO> create(@RequestBody Admin admin) {
        Admin created = adminService.create(admin);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new AdminDTO(created));
    }

    // =========================================================
    // READ ADMIN BY ID
    // =========================================================
    @GetMapping("/read/{id}")
    public ResponseEntity<AdminDTO> read(@PathVariable Long id) {
        Admin admin = adminService.read(id);
        if (admin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new AdminDTO(admin));
    }

    // =========================================================
    // UPDATE ADMIN PROFILE
    // - Uses ID from path for clarity
    // - Preserves role and permission level in AdminService
    // =========================================================
    @PutMapping("/update/{id}")
    public ResponseEntity<AdminDTO> update(@PathVariable Long id, @RequestBody Admin admin) {
        Admin existing = adminService.read(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        //  Enforce correct ID using Builder
        Admin merged = new Admin.Builder()
                .copy(admin)
                .setUserId(id) // enforce ID from path
                .build();

        Admin updated = adminService.update(merged);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new AdminDTO(updated));
    }

    // =========================================================
    // GET ALL ADMINS
    // =========================================================
    @GetMapping("/getAllAdmins")
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

    // =========================================================
    // DELETE ADMIN
    // =========================================================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = adminService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Admin Deleted Successfully");
    }

    // =========================================================
    // CHANGE ADMIN PERMISSION LEVEL
    // - Uses AdminPermissionDTO
    // - Restricted to SUPER_ADMIN
    // =========================================================
    @PutMapping("/changePermission/{id}")
    public ResponseEntity<AdminPermissionDTO> changePermission(
            @PathVariable Long id,
            @RequestBody AdminPermissionDTO dto) {

        Admin admin = adminService.read(id);
        if (admin == null) {
            return ResponseEntity.notFound().build();
        }

        //  Call service method to change permission
        Admin updated = adminService.changePermission(admin, dto.getNewPermissionType());

        // Return updated permission info
        return ResponseEntity.ok(new AdminPermissionDTO(updated));
    }

    // =========================================================
    // APPROVE DRIVER
    // - AllowedAction: "APPROVE_DRIVER"
    // - Transition: UNDER_REVIEW → ACTIVE
    // =========================================================
    @PutMapping("/approveDriver/{id}")
    public ResponseEntity<DriverDTO> approveDriver(@PathVariable Long id, @RequestBody Admin admin) {
        Driver driver = driverService.read(id);
        if (driver == null) {
            return ResponseEntity.notFound().build();
        }
        adminService.approveDriver(id, admin);
        return ResponseEntity.ok(new DriverDTO(driverService.read(id)));
    }

    // =========================================================
    // REJECT DRIVER
    // - AllowedAction: "REJECT_DRIVER"
    // - Transition: UNDER_REVIEW → REJECTED
    // =========================================================
    @PutMapping("/rejectDriver/{id}")
    public ResponseEntity<DriverDTO> rejectDriver(@PathVariable Long id, @RequestBody Admin admin) {
        Driver driver = driverService.read(id);
        if (driver == null) {
            return ResponseEntity.notFound().build();
        }
        adminService.rejectDriver(id, admin);
        return ResponseEntity.ok(new DriverDTO(driverService.read(id)));
    }

    // =========================================================
    // SUSPEND USER (Driver or Parent)
    // - AllowedAction: "SUSPEND_USER"
    // - Transition: ACTIVE → SUSPENDED
    // =========================================================
    @PutMapping("/suspendUser/{id}")
    public ResponseEntity<?> suspendUser(@PathVariable Long id, @RequestBody Admin admin) {
        Driver driver = driverService.read(id);
        if (driver != null) {
            adminService.suspendUser(driver, admin);
            return ResponseEntity.ok(new DriverDTO(driverService.read(id)));
        }

        //If you have ParentService injected, handle parent suspension here
        Parent parent = parentService.read(id);
        if (parent != null) {
            adminService.suspendUser(parent, admin);
            return ResponseEntity.ok(new ParentDTO(parentService.read(id)));
        }

        return ResponseEntity.notFound().build();
    }
}