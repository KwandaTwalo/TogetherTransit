package com.kwndtwalo.TogetherTransit.repository.users;

import com.kwndtwalo.TogetherTransit.domain.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByFirstNameAndLastName(String firstName, String lastName);
}
