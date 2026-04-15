package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.factory.users.ParentFactory;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.repository.users.IParentRepository;
import com.kwndtwalo.TogetherTransit.repository.users.IRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService implements IParentService {

    private final IParentRepository parentRepository;
    private final IRoleRepository roleRepository;

    @Autowired
    public ParentService(IParentRepository parentRepository, IRoleRepository roleRepository) {
        this.parentRepository = parentRepository;
        this.roleRepository = roleRepository;
    }

    /*
     * Create or return existing Parent.
     * Business rule:
     * Same first name + last name = same Parent (duplicate prevention).
     */
    @Override
    public Parent create(Parent parent) {
        if (parent == null) {
            return null;
        }

        return parentRepository
                .findByFirstNameAndLastName(
                        parent.getFirstName(),
                        parent.getLastName())
                .orElseGet(() -> parentRepository.save(parent));
    }

    @Override
    public Parent read(Long Id) {
        return parentRepository.findById(Id).orElse(null);
    }

    /*
     * Update an existing driver.
     * Business rule:
     * You cannot update a driver that does not exist.
     */
    @Override
    public Parent update(Parent parent) {
        if (parent == null || parent.getUserId() == null) {
            return null;
        }

        Parent existing = parentRepository.findById(parent.getUserId()).orElse(null);

        if (existing == null) {
            return null; // cannot update non-existing parent
        }

         // Use Builder COPY pattern to preserve existing state and only update fields that are allowed to change
        Parent merged = new Parent.Builder()
                .copy(existing)
                //.setUserId(existing.getUserId()) // ID must remain the same
                .setFirstName(parent.getFirstName())
                .setLastName(parent.getLastName())
                .setCreatedAt(existing.getCreatedAt()) // preserve original creation date
                // .setAccountStatus(parent.getAccountStatus())
                .setContact(parent.getContact())
                .setAddress(parent.getAddress())
                .setAuthentication(parent.getAuthentication())
                .setRole(existing.getRole()) // Preserving the existing role unless explicitly changed (you can add
                                             // logic to change it if needed)
                .build();

        return parentRepository.save(merged);
    }

    @Override
    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    /*
     * Delete a driver by ID.
     * Returns true only if deletion actually happened.
     */
    @Override
    public boolean delete(Long Id) {
        if (!parentRepository.existsById(Id)) {
            return false;
        }
        parentRepository.deleteById(Id);
        return true;
    }

    // =========================================================
    // BUSINESS RULE:
    // Attach relationships AFTER driver creation
    // (THIS is where your Contact/Address/Auth/Role belong)
    // =========================================================
    public Parent attachRelations(Parent parent,
            Contact contact,
            Address address,
            Authentication authentication,
            Role role) {

        if (parent == null) {
            return null;
        }

        Parent updated = new Parent.Builder()
                .copy(parent)
                .setContact(contact)
                .setAddress(address)
                .setAuthentication(authentication)
                .setRole(role)
                .build();

        return parentRepository.save(updated);
    }

    // =========================================================
    // BUSINESS RULE:
    // Full registration flow (used by controller)
    // Keeps controller clean
    // =========================================================
    public Parent registerParent(String firstName,
            String lastName,
            User.AccountStatus status,
            Contact contact,
            Address address,
            Authentication authentication,
            Role role) {

        // Enforcing defualts
        User.AccountStatus enforcedStatus = User.AccountStatus.ACTIVE; // Parents are auto-approved for simplicity

        // Fetch persisted role from DB
        Role enforcedRole = roleRepository.findByRoleName(Role.RoleName.PARENT)
                .orElseThrow(() -> new RuntimeException("Default PARENT role not found in database"));

        Parent parent = ParentFactory.createParent(
                firstName,
                lastName,
                java.time.LocalDate.now(),
                enforcedStatus); // Enforce default status

        if (parent == null) {
            return null; // Factory validation failed
        }

        Parent enriched = attachRelations(parent,
                contact,
                address,
                authentication,
                enforcedRole);

        return parentRepository.save(enriched);

    }
}
