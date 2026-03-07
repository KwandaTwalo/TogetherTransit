package com.kwndtwalo.TogetherTransit.repository.child;

import com.kwndtwalo.TogetherTransit.domain.child.Child;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IChildRepository extends JpaRepository<Child, Long> {

    // ---------------------------------
    // PARENT-BASED QUERIES
    // ---------------------------------

    // Fetch all children belonging to a parent
    List<Child> findByParent(Parent parent);

    // Count children per parent (capacity + reporting)
    long countByParent(Parent parent);


    // ---------------------------------
    // DUPLICATE PREVENTION
    // ---------------------------------

    // Prevent duplicate children per parent
    boolean existsByParentAndFirstNameAndLastNameAndDateOfBirth(
            Parent parent,
            String firstName,
            String lastName,
            LocalDate dateOfBirth
    );


    // ---------------------------------
    // SEARCH & FILTER
    // ---------------------------------

    // Find children by grade
    List<Child> findByGrade(String grade);

    // Search by partial name match
    List<Child> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName,
            String lastName
    );


    // ---------------------------------
    // VALIDATION
    // ---------------------------------

    // Used before deleting parent (referential safety)
    boolean existsByParent(Parent parent);


    // ---------------------------------
    // OPTIONAL BUSINESS QUERY
    // ---------------------------------

    // Fetch child by full identity match
    Optional<Child> findByParentAndFirstNameAndLastNameAndDateOfBirth(
            Parent parent,
            String firstName,
            String lastName,
            LocalDate dateOfBirth
    );
}