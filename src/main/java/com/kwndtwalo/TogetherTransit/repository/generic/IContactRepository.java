package com.kwndtwalo.TogetherTransit.repository.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IContactRepository extends JpaRepository<Contact, Long> {

    // Find contact using main phone number
    Optional<Contact> findByPhoneNumber(String phoneNumber);

    // Prevent duplicate phone numbers
    boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT c FROM Contact c WHERE c.phoneNumber LIKE %:digits")
    List<Contact> searchByPartialPhone(@Param("digits") String digits);

}
