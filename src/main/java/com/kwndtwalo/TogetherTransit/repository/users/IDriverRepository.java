package com.kwndtwalo.TogetherTransit.repository.users;

import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByFirstNameAndLastName(String firstName, String lastName);

}
