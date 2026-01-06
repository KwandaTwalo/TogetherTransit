package com.kwndtwalo.TogetherTransit.repository.users;

import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IParentRepository extends JpaRepository<Parent, Long> {

    List<Parent> findByFirstNameAndLastName(String firstName, String lastName);
}
