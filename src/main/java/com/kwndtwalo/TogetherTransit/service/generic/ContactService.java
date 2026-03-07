package com.kwndtwalo.TogetherTransit.service.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.repository.generic.IContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContactService implements IContactService {

    private final IContactRepository contactRepository;

    @Autowired
    public ContactService(IContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // -------------------------
    // CRUD OPERATIONS
    // -------------------------

    /**
     * Create or return existing contact (duplicate prevention).
     * Business rule: same phone + emergency number = same contact.
     */
    @Override
    public Contact create(Contact contact) {
        if (contact == null) {
            return null;
        }

        return contactRepository
                .findByPhoneNumberAndEmergencyNumber(
                        contact.getPhoneNumber(),
                        contact.getEmergencyNumber()
                )
                .orElseGet(() -> contactRepository.save(contact));
    }

    @Override
    public Contact read(Long id) {
        return contactRepository.findById(id).orElse(null);
    }

    @Override
    public Contact update(Contact contact) {
        if (contact == null || contact.getContactId() == null) {
            return null;
        }

        if (!contactRepository.existsById(contact.getContactId())) {
            return null;
        }

        return contactRepository.save(contact);
    }

    @Override
    public boolean delete(Long id) {
        if (!contactRepository.existsById(id)) {
            return false;
        }
        contactRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // -------------------------
    // CUSTOM QUERY METHODS
    // -------------------------

    public Contact getByPhoneNumber(String phoneNumber) {
        return contactRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    public Contact getByPhoneAndEmergency(String phoneNumber, String emergencyNumber) {
        return contactRepository
                .findByPhoneNumberAndEmergencyNumber(phoneNumber, emergencyNumber)
                .orElse(null);
    }

    public List<Contact> searchByPartialPhone(String digits) {
        return contactRepository.searchByPartialPhone(digits);
    }
}

