package com.kwndtwalo.TogetherTransit.dto.school;

import com.kwndtwalo.TogetherTransit.domain.school.School;

public class SchoolDTO {

    private Long schoolId;
    private String schoolName;

    public SchoolDTO(School school) {
        this.schoolId = school.getSchoolId();
        this.schoolName = school.getSchoolName();
    }

    @Override
    public String toString() {
        return "\nSchoolDTO{" +
                "schoolId=" + schoolId +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }
}