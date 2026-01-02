package com.kwndtwalo.TogetherTransit.factory.auth;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationFactoryTest {

    private Authentication authentication1 = AuthenticationFactory.createAuthentication("218120192@mycput.ac.za",
            "Anda@2025", LocalDateTime.now(), false);

    @Test
    void createAuthentication() {
        assertNotNull(authentication1);
        System.out.println("Authentication created successfully: " + authentication1);
    }

    @Test
    void testInvalidEmailAddress() {
        Authentication authentication2 = AuthenticationFactory.createAuthentication("218120192mycput.ac.za",
                "Anda@2025", LocalDateTime.now(), false);
        assertNull(authentication2);
        System.out.println("Invalid email address: " + authentication2);
    }

    @Test
    void testInValidPassword() {
        Authentication authentication3 = AuthenticationFactory.createAuthentication("218120192@mycput.ac.za",
                "Anda@Twalo", LocalDateTime.now(), false);
        assertNull(authentication3);
        System.out.println("Invalid password: " + authentication3);
    }
}