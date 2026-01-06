package com.kwndtwalo.TogetherTransit.repository.users;

import com.kwndtwalo.TogetherTransit.domain.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAdminRepository extends JpaRepository<Admin, Long> {

    List<Admin> findByFirstNameAndLastName(String firstName, String lastName);
}
