package com.kwndtwalo.TogetherTransit.domain.school;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SchoolId;
    private String SchoolName;

    protected School() {}

    private School(Builder builder) {
        SchoolId = builder.SchoolId;
        SchoolName = builder.SchoolName;
    }

    public Long getSchoolId() {
        return SchoolId;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    @Override
    public String toString() {
        return "School{" +
                "SchoolId=" + getSchoolId() +
                ", SchoolName='" + getSchoolName() + '\'' +
                '}';
    }

    public static class Builder {
        private Long SchoolId;
        private String SchoolName;

        public Builder setSchoolId(Long SchoolId) {
            this.SchoolId = SchoolId;
            return this;
        }
        public Builder setSchoolName(String SchoolName) {
            this.SchoolName = SchoolName;
            return this;
        }
        public Builder copy(School school) {
            SchoolId = school.getSchoolId();
            SchoolName = school.getSchoolName();
            return this;
        }

        public School build() {return new School(this);}
    }
}
