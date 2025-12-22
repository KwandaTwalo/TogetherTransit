package com.kwndtwalo.TogetherTransit.domain.child;

import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long childId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String grade;

    @ManyToOne(fetch = FetchType.LAZY)//chose this because Cascade type.All when you delete child it will delete the parent also.
    @JoinColumn(name = "parentId")
    private Parent parent;

    protected Child() {}

    private Child (Builder builder) {
        this.childId = builder.childId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dateOfBirth = builder.dateOfBirth;
        this.grade = builder.grade;
        this.parent = builder.parent;
    }

    public Long getChildId() {
        return childId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGrade() {
        return grade;
    }

    public Parent getParent() {
        return parent;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Child child = (Child) o;
        return childId != null && childId.equals(child.childId);
    }

    @Override
    public int hashCode () {
        return 31;
    }

    @Override
    public String toString() {
        return "Child{" +
                "childId=" + getChildId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", dateOfBirth=" + getDateOfBirth() +
                ", grade='" + getGrade() + '\'' +
                ", parent=" + getParent() +
                '}';
    }

    public static class Builder {
        private Long childId;
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private String grade;
        private Parent parent;

        public Builder setChildId(Long childId) {
            this.childId = childId;
            return this;
        }
        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }
        public Builder setGrade(String grade) {
            this.grade = grade;
            return this;
        }
        public Builder setParent(Parent parent) {
            this.parent = parent;
            return this;
        }

        public Builder copy(Child child) {
            this.childId = child.getChildId();
            this.firstName = child.getFirstName();
            this.lastName = child.getLastName();
            this.dateOfBirth = child.getDateOfBirth();
            this.grade = child.getGrade();
            this.parent = child.getParent();
            return this;
        }

        public Child build() {return new Child(this);}
    }
}
