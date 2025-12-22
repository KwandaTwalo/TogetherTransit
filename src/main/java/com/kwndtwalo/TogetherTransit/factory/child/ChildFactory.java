package com.kwndtwalo.TogetherTransit.factory.child;

import com.kwndtwalo.TogetherTransit.domain.child.Child;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.util.Helper;

import java.time.LocalDate;

public class ChildFactory {

    public static Child createChild(String firstName, String lastName, LocalDate dateOfBirth,String grade, Parent parent) {

        if (!Helper.isValidString(firstName) || !Helper.isValidString(lastName) || !Helper.isValidGrade(grade)) {
            return null;
        }

        if (!Helper.isValidDateOfBirth(dateOfBirth)) {
            return null;
        }

        return new Child.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setDateOfBirth(dateOfBirth)
                .setGrade(grade)
                .setParent(parent)
                .build();
    }
}
