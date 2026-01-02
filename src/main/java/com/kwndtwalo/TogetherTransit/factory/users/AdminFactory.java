package com.kwndtwalo.TogetherTransit.factory.users;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.users.Admin;
import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.util.Helper;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AdminFactory {

    public static Admin createAdmin(LocalDateTime lastLogin, String firstName, String lastName, LocalDate createdAt,
                                    User.AccountStatus accountStatus, Contact contact, Address address,
                                    Authentication authentication, Role role, PermissionLevel permissionLevel) {
        if (!Helper.isValidString(firstName) || !Helper.isValidString(lastName)) {
            return null;
        }

        return new Admin.Builder()
                .setLastLogin(lastLogin)
                .setFirstName(firstName)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setCreatedAt(createdAt)
                .setAccountStatus(accountStatus)
                .setContact(contact)
                .setAddress(address)
                .setAuthentication(authentication)
                .setRole(role)
                .setPermissionLevel(permissionLevel)
                .build();
    }

}
