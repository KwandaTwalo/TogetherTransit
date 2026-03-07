package com.kwndtwalo.TogetherTransit.service.authentication;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.repository.auth.IAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthenticationService implements IAuthenticationService {


    private IAuthenticationRepository authenticationRepository;

    @Autowired
    public AuthenticationService(IAuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }


    /*
     * Create a new Authentication record.
     * Business rule:
     * - Email must be unique (no duplicate accounts).
     */
    @Override
    public Authentication create(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        if (authenticationRepository.existsByEmailAddress(authentication.getEmailAddress())) {
            // Return existing account instead of duplicating.
            return authenticationRepository
                    .findByEmailAddress(authentication.getEmailAddress())
                    .orElse(null);
        }
        return authenticationRepository.save(authentication);
    }

    /*
     * Read Authentication by ID.
     */
    @Override
    public Authentication read(Long Id) {
        return authenticationRepository.findById(Id).orElse(null);
    }

    /*
     * Update Authentication details.
     * Business rule:
     * - Record must already exist.
     */
    @Override
    public Authentication update(Authentication authentication) {
        if (authentication == null || authentication.getAuthenticationId() == null) {
            return null;
        }

        if(!authenticationRepository.existsById(authentication.getAuthenticationId())) {
            return null;
        }
        return authenticationRepository.save(authentication);
    }

    @Override
    public List<Authentication> getAuthentications() {
        return authenticationRepository.findAll();
    }

    /*Delete By authentication ID*/
    @Override
    public boolean delete(Long Id) {
       if (!authenticationRepository.existsById(Id)) {
           return false;
       }
       authenticationRepository.deleteById(Id);
       return true;
    }

    /** -------------------------------------------------
     * CUSTOM QUERY METHODS
     * -------------------------------------------------*/

    /*Login lookup*/
    public Authentication findByEmailAddress(String emailAddress) {
        return authenticationRepository.findByEmailAddress(emailAddress).orElse(null);
    }

    /*Check if email already exists*/
    public boolean emailAddressExists(String emailAddress) {
        return authenticationRepository.existsByEmailAddress(emailAddress);
    }

    /*Locked accounts (security/admin*/
    public List<Authentication> getLockedAccounts() {
        return authenticationRepository.findByLockedTrue();
    }

    /* Active (unlocked) accounts */
    public List<Authentication> getUnlockedAccounts() {
        return authenticationRepository.findByLockedFalse();
    }

    /*Login allowed only if account is unlocked */
    public Authentication findActiveByEmailAddress(String emailAddress) {
        return authenticationRepository
                .findByEmailAddressAndLockedFalse(emailAddress)
                .orElse(null);
    }

    /*Accounts that never logged in */
    public List<Authentication> getNeverLoggedInAccounts() {
        return authenticationRepository.findByLastLoginIsNull();
    }

    /* Inactive accounts before a certain date */
    public List<Authentication> getInactiveAccountsBefore(LocalDateTime date) {
        return authenticationRepository.findByLastLoginBefore(date);
    }

    /* Bulk lookup by email domain */
    public List<Authentication> searchByEmailDomain(String domain) {
        return authenticationRepository.findByEmailAddressContainingIgnoreCase(domain);
    }


}
