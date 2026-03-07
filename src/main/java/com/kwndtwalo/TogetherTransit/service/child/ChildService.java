package com.kwndtwalo.TogetherTransit.service.child;

import com.kwndtwalo.TogetherTransit.domain.child.Child;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.repository.child.IChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildService implements IChildService{

    private final IChildRepository childRepository;

    @Autowired
    public ChildService(IChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    /**
     * Create new child profile.
     * Enforces duplicate prevention.
     */
    @Override
    public Child create(Child child) {
        if (child == null) {return null;}

        // Prevent duplicate child entries
        boolean exists = childRepository.existsByParentAndFirstNameAndLastNameAndDateOfBirth(
                child.getParent(),
                child.getFirstName(),
                child.getLastName(),
                child.getDateOfBirth()
        );

        if (exists) {
            System.out.println("Child already exists");
            return null;
        }
        return childRepository.save(child);
    }

    @Override
    public Child read(Long Id) {
        return childRepository.findById(Id).orElse(null);
    }

    @Override
    public Child update(Child child) {
       if (child == null || child.getChildId() == null) {return null;}
       if (!childRepository.existsById(child.getChildId())) {return null;}
       return childRepository.save(child);
    }

    @Override
    public List<Child> getAllChildren() {
        return childRepository.findAll();
    }

    /**
     * Soft delete pattern not applied → full delete allowed
     */
    @Override
    public boolean delete(Long Id) {
        if (!childRepository.existsById(Id)) {return false;}

        childRepository.deleteById(Id);
        return true;
    }

    // -------------------------
    // EXTRA BUSINESS METHODS
    // -------------------------

    public List<Child> getChildrenByParent(Parent parent) {
        return childRepository.findByParent(parent);
    }

    public long countChildrenForParent(Parent parent) {
        return childRepository.countByParent(parent);
    }


}
