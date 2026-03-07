package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.repository.users.IParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService implements IParentService {

    private IParentRepository parentRepository;


    @Autowired
    public ParentService(IParentRepository parentRepository) {
        this.parentRepository = parentRepository;
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
                        parent.getLastName()
                )
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

        if (!parentRepository.existsById(parent.getUserId())) {
            return null;
        }
        return parentRepository.save(parent);
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
}
