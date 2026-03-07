package com.kwndtwalo.TogetherTransit.service.authentication;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.factory.auth.AuthenticationFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    private static Authentication savedAuth;

    @Test
    void a_create() {
        Authentication createdAuth = AuthenticationFactory.createAuthentication(
                "sandile@gmail.com",
                "San@1234",
                LocalDateTime.now(),
                false
        );
        assertNotNull(createdAuth);
        savedAuth = authenticationService.create(createdAuth);
        assertNotNull(savedAuth);
        assertNotNull(savedAuth.getAuthenticationId());
        System.out.println("Authentication created successfully: " + savedAuth);
    }

    @Test
    void b_read() {
        Authentication readAuth = authenticationService.read(savedAuth.getAuthenticationId());
        assertNotNull(readAuth);
        assertEquals(savedAuth.getAuthenticationId(), readAuth.getAuthenticationId());
        System.out.println("Authentication read successfully: " + readAuth);
    }

    @Test
    void c_update() {
        Authentication updatedAuth = new Authentication.Builder()
                .copy(savedAuth)
                .setEmailAddress("sandile@mycput.ac.za")
                .build();

        Authentication result = authenticationService.update(updatedAuth);
        assertNotNull(result);
        assertEquals("sandile@mycput.ac.za", result.getEmailAddress());

        savedAuth = result;

        System.out.println("Authentication updated successfully: " + result);
    }

    @Test
    void d_getAuthentications() {
        List<Authentication> authentications = authenticationService.getAuthentications();
        assertFalse(authentications.isEmpty());
        System.out.println("Authentications found: " + authentications);
    }

    @Test
    void e_findByEmailAddress() {
        Authentication foundAuth = authenticationService.findByEmailAddress("sandile@mycput.ac.za");
        assertNotNull(foundAuth);
        System.out.println("Found email address: " + foundAuth);
    }

    @Test
    void h_getUnlockedAccounts() {
        List<Authentication> unlockedAccounts = authenticationService.getUnlockedAccounts();
        assertNotNull(unlockedAccounts);
        System.out.println("Unlocked accounts found: " + unlockedAccounts);
    }

    @Test
    void j_getNeverLoggedInAccounts() {
        List<Authentication> neverLoggedInAccounts = authenticationService.getNeverLoggedInAccounts();
        assertNotNull(neverLoggedInAccounts);
        System.out.println("Never logged in: " + neverLoggedInAccounts);
    }


    @Test
    void l_searchByEmailDomain() {
        List<Authentication> accounts = authenticationService.searchByEmailDomain("mycput.ac.za");
        assertNotNull(accounts);
        System.out.println("Accounts found: " + accounts);
    }

    @Test
    void m_delete() {
        boolean deleted = authenticationService.delete(savedAuth.getAuthenticationId());
        assertTrue(deleted);
        System.out.println("Authentication deleted successfully: " + savedAuth);
    }
}