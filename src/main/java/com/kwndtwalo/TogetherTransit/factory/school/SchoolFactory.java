package com.kwndtwalo.TogetherTransit.factory.school;

import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.util.Helper;

public class SchoolFactory {

    public static School createSchool(String schoolName) {

        if (!Helper.isValidString(schoolName)) {
            return null;
        }

        return new School.Builder()
                .setSchoolName(schoolName)
                .build();
    }
}
