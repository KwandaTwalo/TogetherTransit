package com.kwndtwalo.TogetherTransit.controller.user;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.dto.auth.AuthenticationDTO;
import com.kwndtwalo.TogetherTransit.dto.generic.AddressDTO;
import com.kwndtwalo.TogetherTransit.dto.generic.ContactDTO;
import com.kwndtwalo.TogetherTransit.dto.users.ParentDTO;
import com.kwndtwalo.TogetherTransit.service.users.ParentService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    // =========================================
    // CREATE DRIVER (FULL REGISTRATION FLOW)
    // =========================================
    @PostMapping("/create")
    public ResponseEntity<?> createParent(@Valid @RequestBody ParentDTO dto,
            BindingResult result) {
        // 1. DTO validation
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Authentication auth = null;
        if (dto.getAuthentication() != null) {
            auth = new Authentication.Builder()
                    .setEmailAddress(dto.getAuthentication().getEmailAddress())
                    .setPassword(dto.getAuthentication().getPassword())
                    .setLastLogin(dto.getAuthentication().getLastLogin())
                    .setLocked(dto.getAuthentication().isLocked())
                    .build();
        }

        Parent created = parentService.registerParent(
                dto.getFirstName(),
                dto.getLastName(),
                null, // status ignored, service enforces ACTIVE
                null, // Contact (can be added later)
                null, // Address
                auth, // Authentication
                null // Role (service should assign default PARENT role)

        );

        if (created == null) {
            return ResponseEntity.badRequest().body("Failed to create parent. Possible duplicate or invalid data.");
        }

        return ResponseEntity.ok(new ParentDTO(created));
    }

    // =========================================
    // READ DRIVER
    // =========================================
    @GetMapping("/read/{id}")
    public ResponseEntity<?> read(@PathVariable long id) {
        Parent parent = parentService.read(id);

        if (parent == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ParentDTO(parent));
    }

    // =========================================
    // UPDATE DRIVER
    // =========================================
    @PutMapping("/update/{Id}")
    public ResponseEntity<?> update(@Valid @RequestBody ParentDTO dto,
            @PathVariable long Id, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Address address = null; // You can add logic to convert DTO address fields to an Address object
        Contact contact = null; // Similarly, convert DTO contact fields to a Contact object

        // CONVERT ADDRESS DTO → ENTITY
        if (dto.getAddress() != null) {
            address = new Address.Builder()
                    .setStreetNumber(dto.getAddress().getStreetNumber())
                    .setStreetName(dto.getAddress().getStreetName())
                    .setSuburb(dto.getAddress().getSuburb())
                    .setCity(dto.getAddress().getCity())
                    .setPostalCode(dto.getAddress().getPostalCode())
                    .build();
        }

        // CONVERT CONTACT DTO → ENTITY
        if (dto.getContact() != null) {
            contact = new Contact.Builder()
                    .setPhoneNumber(dto.getContact().getPhoneNumber())
                    .setEmergencyNumber(dto.getContact().getEmergencyNumber())
                    .build();
        }

        Parent updateParent = new Parent.Builder()
                .setUserId(Id)
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                // .setAccountStatus(dto.getAccountStatus()) // You can add logic to convert DTO
                // status to AccountStatus enum
                .setContact(contact)
                .setAddress(address)
                // .setAuthentication(...) // Add logic if you want to update authentication
                // details
                // .setRole(...) // Add logic if you want to update role
                .build();

        Parent updated = parentService.update(updateParent);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ParentDTO(updated));
    }

    // =========================================
    // GET ALL
    // =========================================
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

    // =========================================
    // DELETE
    // =========================================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {

        boolean deleted = parentService.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Parent deleted successfully");
    }

    @PutMapping("/{id}/address")
    public ResponseEntity<?> attachAddress(
            @PathVariable Long id,
            @RequestBody AddressDTO dto) {

        Parent parent = parentService.read(id);

        if (parent == null) {
            return ResponseEntity.notFound().build();
        }

        Address address = new Address.Builder()
                .setStreetNumber(dto.getStreetNumber())
                .setStreetName(dto.getStreetName())
                .setSuburb(dto.getSuburb())
                .setCity(dto.getCity())
                .setPostalCode(dto.getPostalCode())
                .build();

        Parent updated = parentService.attachRelations(
                parent,
                parent.getContact(),
                address,
                parent.getAuthentication(),
                parent.getRole());

        return ResponseEntity.ok(new ParentDTO(updated));
    }

    @PutMapping("/{id}/contact")
    public ResponseEntity<?> attachContact(
            @PathVariable Long id,
            @Valid @RequestBody ContactDTO dto,
            BindingResult result) {

        // =========================================
        // 1. VALIDATE INPUT (DTO LEVEL)
        // =========================================
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // =========================================
        // 2. FIND EXISTING PARENT
        // =========================================
        Parent parent = parentService.read(id);

        if (parent == null) {
            return ResponseEntity.notFound().build();
        }

        // =========================================
        // 3. CONVERT DTO → ENTITY
        // =========================================
        Contact contact = new Contact.Builder()
                .setPhoneNumber(dto.getPhoneNumber())
                .setEmergencyNumber(dto.getEmergencyNumber())
                .build();

        // =========================================
        // 4. ATTACH USING SERVICE LAYER
        // (IMPORTANT: DO NOT BUILD PARENT HERE)
        // =========================================
        Parent updated = parentService.attachRelations(
                parent,
                contact,
                parent.getAddress(),
                parent.getAuthentication(),
                parent.getRole());

        // =========================================
        // 5. RETURN RESPONSE
        // =========================================
        return ResponseEntity.ok(new ParentDTO(updated));
    }

    // add authentication.
    @PutMapping("/{id}/authentication")
    public ResponseEntity<?> attachAuthentication(
            @PathVariable Long id,
            @Valid @RequestBody AuthenticationDTO dto,
            BindingResult result) {

        // =========================================
        // 1. VALIDATE INPUT (DTO LEVEL)
        // =========================================
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // =========================================
        // 2. FIND EXISTING PARENT
        // =========================================
        Parent parent = parentService.read(id);

        if (parent == null) {
            return ResponseEntity.notFound().build();
        }

        // =========================================
        // 3. CONVERT DTO → ENTITY
        // =========================================
        Authentication authentication = new Authentication.Builder()
                .setEmailAddress(dto.getEmailAddress())
                .setPassword(dto.getPassword())
                .setLastLogin(dto.getLastLogin())
                .setLocked(dto.isLocked())
                .build();

        // =========================================
        // 4. ATTACH USING SERVICE LAYER
        // (IMPORTANT: DO NOT BUILD PARENT HERE)
        // =========================================
        Parent updated = parentService.attachRelations(
                parent,
                parent.getContact(),
                parent.getAddress(),
                authentication,
                parent.getRole());

        // =========================================
        // 5. RETURN RESPONSE
        // =========================================
        return ResponseEntity.ok(new ParentDTO(updated));
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        Parent parent = parentService.findByEmail(email);
        if (parent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ParentDTO(parent));
    }
}
