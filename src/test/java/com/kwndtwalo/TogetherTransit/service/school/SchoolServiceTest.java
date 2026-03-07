package com.kwndtwalo.TogetherTransit.service.school;

import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.factory.school.SchoolFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class SchoolServiceTest {

    @Autowired
    private SchoolService schoolService;

    private static School savedSchool;

    @Test
    void a_create() {
        School created = SchoolFactory.createSchool("Kwa-Komani Comprehensive School");
        savedSchool = schoolService.create(created);
        assertNotNull(savedSchool);
        assertNotNull(savedSchool.getSchoolId());
        System.out.println("School created successfully: " + savedSchool);
    }

    @Test
    void b_read() {
        School read = schoolService.read(savedSchool.getSchoolId());
        assertNotNull(read);
        assertEquals(savedSchool.getSchoolId(), read.getSchoolId());
        System.out.println("School read successfully: " + read);
    }

    @Test
    void c_update() {
        School updated = new School.Builder()
                .copy(savedSchool)
                .setSchoolName("Kwa Komani High School")
                .build();

        School result = schoolService.update(updated);
        assertNotNull(result);
        assertEquals(updated.getSchoolId(), result.getSchoolId());
        assertEquals("Kwa Komani High School", result.getSchoolName());
    }

    @Test
    void d_getAllSchools() {
        List<School> schools = schoolService.getAllSchools();
        assertNotNull(schools);
        assertFalse(schools.isEmpty());
        System.out.println("Schools in the system: " + schools);
    }

    @Test
    void e_delete() {
        boolean deleted = schoolService.delete(savedSchool.getSchoolId());
        assertTrue(deleted);
        System.out.println("School deleted successfully: " + savedSchool);
    }
}