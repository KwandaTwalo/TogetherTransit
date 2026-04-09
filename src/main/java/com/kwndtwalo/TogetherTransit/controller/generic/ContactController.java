package com.kwndtwalo.TogetherTransit.controller.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.dto.generic.ContactDTO;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;
import com.kwndtwalo.TogetherTransit.service.generic.ContactService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /*
     * CREATE Contact
     * ---------------
     * Business Rules:
     * 1. A contact is considered UNIQUE by:
     * - phoneNumber
     * - emergencyNumber
     *
     * 2. If the same contact already exists,
     * the system RETURNS the existing record instead
     * of creating a duplicate.
     *
     * Why this matters:
     * - Prevents duplicate contact records
     * - Keeps the database clean
     * - Avoids unnecessary repeated data
     */
    @PostMapping("/create") // Expose an endpoint at /contact/create
    public ResponseEntity<?> create(
            @Valid @RequestBody ContactDTO contactDTO, // 1. Bind JSON body to ContactDTO and run validation annotations
            BindingResult result // 2. Capture validation errors if any
    ) {
        if (result.hasErrors()) { // 3. If validation failed (e.g., blank or invalid phone number)
            return ResponseEntity.badRequest().body(result.getAllErrors()); // 4. Return 400 with error details
        }

        // 5. Use the factory to enforce business rules (e.g., valid format, phone ≠
        // emergency)
        Contact contact = ContactFactory.createContact(
                contactDTO.getPhoneNumber(),
                contactDTO.getEmergencyNumber());

        if (contact == null) { // 6. Factory returned null → business rule failed
            return ResponseEntity.badRequest().body("Invalid contact data (format or business rule failed)");
        }

        // 7. Delegate to service layer → checks for duplicates before saving
        Contact created = contactService.create(contact);

        if (created == null) { // 8. Service returned null → duplicate contact already exists
            return ResponseEntity.badRequest().body("Duplicate contact exists");
        }

        // 9. If everything passed, return 200 OK with the created contact
        return ResponseEntity.ok(created);
    }

    /*
     * READ Contact BY ID
     * -------------------
     * Business Rules:
     * - Fetch a single contact using its database ID.
     *
     * When used:
     * - Admin debugging
     * - Viewing contact details
     * - Linking contact to another entity
     */
    @GetMapping("/read/{Id}")
    public ResponseEntity<Contact> read(@PathVariable Long Id) {
        Contact readContact = contactService.read(Id);

        if (readContact == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(readContact);
    }

    /*
     * UPDATE Contact
     * ---------------
     * Updates an existing contact using the ID from the URL.
     *
     * Business Rules:
     * 1. The request body must not be null.
     * 2. The ID in the URL determines exactly which record to update.
     * 3. If the body already has an ID and it does not match the URL ID,
     * the request is rejected.
     * 4. Only an existing contact can be updated.
     *
     * Why this matters:
     * - Prevents updating the wrong contact
     * - Keeps the API RESTful and clear
     * - Works well with the Builder pattern since Contact has no setters
     */
    @PutMapping("/update/{Id}") // Endpoint: /contact/update/{Id}
    public ResponseEntity<?> update(
            @Valid @RequestBody ContactDTO contactDTO, // 1. Validate incoming JSON using DTO
            BindingResult result, // 2. Capture validation errors
            @PathVariable Long Id // 3. Path variable for the record ID
    ) {
        if (result.hasErrors()) { // 4. If validation failed
            return ResponseEntity.badRequest().body(result.getAllErrors()); // 5. Return 400 with errors
        }

        // 6. Build domain object with correct ID from URL
        Contact contactToUpdate = new Contact.Builder()
                .setContactId(Id)
                .setPhoneNumber(contactDTO.getPhoneNumber())
                .setEmergencyNumber(contactDTO.getEmergencyNumber())
                .build();

        // 7. Delegate update to service layer
        Contact updated = contactService.update(contactToUpdate);

        if (updated == null) { // 8. If no record found
            return ResponseEntity.notFound().build();
        }

        // 9. Return updated contact
        return ResponseEntity.ok(updated);
    }

    /*
     * DELETE Contact
     * ---------------
     * Business Rules:
     * - Contact must exist before deletion.
     *
     * Caution:
     * - If contact is linked to other records (Parent, Driver),
     * deletion should normally be blocked or carefully handled.
     */
    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<String> delete(@PathVariable Long Id) {

        boolean deleted = contactService.delete(Id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Contact deleted successfully");
    }

    /*
     * GET ALL Contacts
     * -----------------
     * Business Rules:
     * - Returns ALL stored contacts.
     *
     * Used for:
     * - Admin dashboards
     * - Reporting
     * - Data audits
     */
    @GetMapping("/getAllContacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    /*
     * GET Contact BY Phone Number
     * -----------------------------
     * Business Rules:
     * - Each phone number uniquely identifies a contact.
     *
     * Used for:
     * - Quick lookups
     * - Validation checks
     */
    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<Contact> getByPhone(@PathVariable String phoneNumber) {
        Contact contact = contactService.getByPhoneNumber(phoneNumber);
        if (contact == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contact);
    }

    /*
     * GET Contact BY Phone + Emergency Number
     * ----------------------------------------
     * Business Rules:
     * - This represents a FULL uniqueness check.
     *
     * Used mainly:
     * - Internally by create() to prevent duplicates.
     *
     * Example:
     * GET /contact/search?phone=0831234567&emergency=0712223333
     */
    @GetMapping("/search")
    public ResponseEntity<Contact> getByPhoneAndEmergency(
            @RequestParam String phone,
            @RequestParam String emergency) {

        Contact contact = contactService.getByPhoneAndEmergency(phone, emergency);

        if (contact == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(contact);
    }

    /*
     * SEARCH Contacts BY Partial Phone Digits
     * ----------------------------------------
     * Business Rules:
     * - Allows searching using partial phone digits.
     *
     * Used for:
     * - Admin search
     * - UI search autocomplete
     *
     * Example:
     * GET/contact/search/partial/083
     */
    @GetMapping("/search/partial/{digits}")
    public ResponseEntity<List<Contact>> searchByPartialPhone(
            @PathVariable String digits) {

        return ResponseEntity.ok(
                contactService.searchByPartialPhone(digits));
    }
}
