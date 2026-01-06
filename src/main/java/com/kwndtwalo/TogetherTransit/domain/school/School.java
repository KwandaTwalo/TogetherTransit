package com.kwndtwalo.TogetherTransit.domain.school;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schoolId;
    private String schoolName;

    protected School() {}

    private School(Builder builder) {
        schoolId = builder.schoolId;
        schoolName = builder.schoolName;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    @Override
    public String toString() {
        return "School{" +
                "schoolId=" + getSchoolId() +
                ", schoolName='" + getSchoolName() + '\'' +
                '}';
    }

    public static class Builder {
        private Long schoolId;
        private String schoolName;

        public Builder setSchoolId(Long SchoolId) {
            this.schoolId = SchoolId;
            return this;
        }
        public Builder setSchoolName(String SchoolName) {
            this.schoolName = SchoolName;
            return this;
        }
        public Builder copy(School school) {
            schoolId = school.getSchoolId();
            schoolName = school.getSchoolName();
            return this;
        }

        public School build() {return new School(this);}
    }
}
