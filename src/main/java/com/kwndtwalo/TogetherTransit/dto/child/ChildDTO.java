package com.kwndtwalo.TogetherTransit.dto.child;

import com.kwndtwalo.TogetherTransit.domain.child.Child;

import java.time.LocalDate;

public class ChildDTO {

    private Long childId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String grade;
    private Long parentId;
    private String parentName;


    public ChildDTO(Child child) {
        this.childId = child.getChildId();
        this.firstName = child.getFirstName();
        this.lastName = child.getLastName();
        this.dateOfBirth = child.getDateOfBirth();
        this.grade = child.getGrade();

        if (child.getParent() != null) {
            this.parentId = child.getParent().getUserId();
            this.parentName = "Parent Linked"; //added as a safe placeholder so that we know child is linked with this parent.
        }else {
            this.parentName = "N/A";
        }

    }

    @Override
    public String toString() {
        return "\nChildDTO{" +
                "childId=" + childId +
                ", name='" + firstName + " " + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", grade='" + grade + '\'' +
                ", parentId=" + parentId +
                ", parent='" + parentName + '\'' +
                '}';
    }
}