package com.kwndtwalo.TogetherTransit.domain.child;

import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import jakarta.persistence.*;

@Entity
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long childId;
    private String firstName;
    private String lastName;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)//chose this because Cascade type.All when you delete child it will delete the parent also.
    @JoinColumn(name = "parentId")
    private Parent parent;

    protected Child() {}

    private Child (Builder builder) {
        this.childId = builder.childId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
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

    public int getAge() {
        return age;
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

    public static class Builder {
        private Long childId;
        private String firstName;
        private String lastName;
        private int age;
        private Parent parent;

        public Builder childId(Long childId) {
            this.childId = childId;
            return this;
        }
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder age(int age) {
            this.age = age;
            return this;
        }
        public Builder parent(Parent parent) {
            this.parent = parent;
            return this;
        }

        public Builder copy(Child child) {
            this.childId = child.childId;
            this.firstName = child.firstName;
            this.lastName = child.lastName;
            this.age = child.age;
            this.parent = child.parent;
            return this;
        }

        public Child build() {return new Child(this);}
    }
}
