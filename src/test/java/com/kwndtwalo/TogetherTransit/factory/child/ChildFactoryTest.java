package com.kwndtwalo.TogetherTransit.factory.child;

import com.kwndtwalo.TogetherTransit.domain.child.Child;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ChildFactoryTest {

    private Parent mockParent = mock(Parent.class);

    private Child child1 = ChildFactory.createChild("Yongeza", "Twalo",
            LocalDate.of(2014,3,5), "grade 5", mockParent);

    @Test
    void createChild() {
        assertNotNull(child1);
        System.out.println("Child created successfully: " + child1);
    }

    @Test
    void testInvalidDateOfBirth() {
        Child child2 = ChildFactory.createChild("Yongeza", "Twalo",
                LocalDate.now(), "5", mockParent);
        assertNull(child2);
        System.out.println("Child age must be between 3 and 20");
    }

    @Test
    void testInvalidGrade() {
        Child child3 = ChildFactory.createChild("Yongeza", "Twalo",
                LocalDate.of(2014,3,5), "grade 13", mockParent);
        assertNull(child3);
        System.out.println("Child must be in grade 0/R - 12 and you must start with word 'grade' then 'number'.");
    }
}