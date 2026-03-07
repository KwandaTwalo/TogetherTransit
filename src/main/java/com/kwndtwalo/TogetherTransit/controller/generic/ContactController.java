package com.kwndtwalo.TogetherTransit.controller.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.service.generic.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
     *    - phoneNumber
     *    - emergencyNumber
     *
     * 2. If the same contact already exists,
     *    the system RETURNS the existing record instead
     *    of creating a duplicate.
     *
     * Why this matters:
     * - Prevents duplicate contact records
     * - Keeps the database clean
     * - Avoids unnecessary repeated data
     */
    @PostMapping("/create")
    public ResponseEntity<Contact> create(@RequestBody Contact contact) {
        Contact created = contactService.create(contact);

        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
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
     * Business Rules:
     * 1. The contact MUST already exist (must have ID).
     * 2. System only updates valid existing records.
     *
     * Why:
     * - Prevents accidental insertion during update.
     * - Protects database integrity.
     */
    @PutMapping("/update")
    public ResponseEntity<Contact> update(@RequestBody Contact contact) {
        Contact updated = contactService.update(contact);
        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }
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
     *   deletion should normally be blocked or carefully handled.
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
    @GetMapping("/getAll")
    public ResponseEntity<List<Contact>> getAll() {
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

        Contact contact =
                contactService.getByPhoneAndEmergency(phone, emergency);

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
                contactService.searchByPartialPhone(digits)
        );
    }
}
