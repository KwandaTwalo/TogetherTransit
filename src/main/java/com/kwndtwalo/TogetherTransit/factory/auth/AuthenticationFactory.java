package com.kwndtwalo.TogetherTransit.factory.auth;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.util.Helper;

import java.time.LocalDateTime;

public class AuthenticationFactory {

    public static Authentication createAuthentication(String email,
                                                      String password,
                                                      LocalDateTime lastLogin,
                                                      boolean is_locked) {

        if (!Helper.isValidEmailAddress(email) ||
        !Helper.isValidPassword(password)) {
            return null;
        }
        return new Authentication.Builder()
                .setEmailAddress(email)
                .setPassword(password)
                .setLastLogin(lastLogin)
                .setIs_locked(is_locked)
                .build();
    }
}
