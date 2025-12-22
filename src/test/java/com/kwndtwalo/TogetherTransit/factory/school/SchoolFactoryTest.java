package com.kwndtwalo.TogetherTransit.factory.school;

import com.kwndtwalo.TogetherTransit.domain.school.School;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolFactoryTest {

    private School school1 = SchoolFactory.createSchool("Milnerton High School");

    @Test
    void createSchool() {
        assertNotNull(school1);
        assertEquals("Milnerton High School", school1.getSchoolName());
        System.out.println("School Name was created successfully: " + school1);
    }

    @Test
    void createInvalidSchool() {
        School school2 = SchoolFactory.createSchool("");
        assertNull(school2);
        System.out.println(school2);
    }
}