package com.kwndtwalo.TogetherTransit.repository.auth;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IAuthenticationRepository extends JpaRepository<Authentication, Long> {

    // ---------------------------------
    // LOGIN & IDENTITY
    // ---------------------------------

    // Used during login
    Optional<Authentication> findByEmailAddress(String emailAddress);

    // Used during registration to prevent duplicates
    boolean existsByEmailAddress(String emailAddress);


    // ---------------------------------
    // ACCOUNT LOCKING & SECURITY
    // ---------------------------------

    // Find all locked accounts (admin / security audit)
    List<Authentication> findByLockedTrue();

    // Fetch only active (unlocked) accounts
    List<Authentication> findByLockedFalse();

    // Used during login to block locked accounts
    Optional<Authentication> findByEmailAddressAndLockedFalse(String emailAddress);


    // ---------------------------------
    // LOGIN ACTIVITY & AUDITING
    // ---------------------------------

    // Accounts that have never logged in
    List<Authentication> findByLastLoginIsNull();

    // Inactive accounts (e.g., not logged in for X months)
    List<Authentication> findByLastLoginBefore(LocalDateTime dateTime);


    // ---------------------------------
    // PASSWORD / SECURITY MAINTENANCE
    // ---------------------------------

    // Bulk security operations (e.g., force password reset)
    List<Authentication> findByEmailAddressContainingIgnoreCase(String domain);
}
