package com.kwndtwalo.TogetherTransit.controller.auth;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.dto.auth.AuthenticationDTO;
import com.kwndtwalo.TogetherTransit.factory.auth.AuthenticationFactory;
import com.kwndtwalo.TogetherTransit.service.authentication.AuthenticationService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * CREATE Authentication
     * ---------------------
     * Business Rules:
     * - Email must be unique.
     * - Password must meet strength requirements.
     */
    @PostMapping("/create")
    public ResponseEntity<?> create(
            @Valid @RequestBody AuthenticationDTO authDTO, // 1. Validate DTO input
            BindingResult result
    ) {
        if (result.hasErrors()) { // 2. If validation failed
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // 3. Use factory to enforce helper rules (valid email, strong password)
        Authentication auth = AuthenticationFactory.createAuthentication(
                authDTO.getEmailAddress(),
                authDTO.getPassword(),
                authDTO.getLastLogin(),
                authDTO.isLocked()
        );

        if (auth == null) { // 4. Factory rejected invalid input
            return ResponseEntity.badRequest().body("Invalid authentication data");
        }

        // 5. Service enforces uniqueness (no duplicate emails)
        Authentication created = authenticationService.create(auth);

        if (created == null) { // 6. Duplicate email found
            return ResponseEntity.badRequest().body("Email already exists");
        }

        // 7. Return DTO response
        return ResponseEntity.ok(created);
    }

    /**
     * READ Authentication by ID
     */
    @GetMapping("/read/{id}")
    public ResponseEntity<Authentication> read(@PathVariable Long id) {
        Authentication auth = authenticationService.read(id);
        if (auth == null) {
            return ResponseEntity.notFound().build(); // 404 if not found
        }
        return ResponseEntity.ok(auth);
    }

    /**
     * UPDATE Authentication
     * ---------------------
     * Business Rules:
     * - Record must exist.
     * - Email must remain unique.
     */
    @PutMapping("/update/{Id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody AuthenticationDTO authDTO, // 1. Validate DTO input
            BindingResult result,
            @PathVariable Long Id
    ) {
        if (result.hasErrors()) { // 2. Validation failed
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // 3. Build domain object with correct ID
        Authentication authToUpdate = new Authentication.Builder()
                .setAuthenticationId(Id)
                .setEmailAddress(authDTO.getEmailAddress())
                .setPassword(authDTO.getPassword())
                .setLastLogin(authDTO.getLastLogin())
                .setLocked(authDTO.isLocked())
                .build();

        // 4. Service enforces existence check
        Authentication updated = authenticationService.update(authToUpdate);

        if (updated == null) { // 5. Record not found
            return ResponseEntity.notFound().build();
        }

        // 6. Return updated DTO
        return ResponseEntity.ok(updated);
    }

    /**
     * GET All Authentications
     */
    @GetMapping("/getAllAuthentications")
    public ResponseEntity<List<Authentication>> getAllAuthentications() {
        List<Authentication> authList = authenticationService.getAuthentications();

        return ResponseEntity.ok(authList);
    }

    /**
     * DELETE Authentication by ID
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        boolean deleted = authenticationService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build(); // 404 if not found
        }
        return ResponseEntity.ok("Authentication deleted successfully");
    }

}
