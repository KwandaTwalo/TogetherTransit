package com.kwndtwalo.TogetherTransit.domain.users;

import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long userId;
    protected String firstName;
    protected String lastName;
    protected LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    protected AccountStatus accountStatus;

    public enum AccountStatus {
        UNDER_REVIEW,
        ACTIVE,
        REJECTED,
        SUSPENDED,
    }

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contactId", referencedColumnName = "contactId")
    protected Contact contact;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "addressId", referencedColumnName = "addressId")
    protected Address address;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "authenticationId", referencedColumnName = "authenticationId")
    protected Authentication authentication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    protected Role role;

    protected User() {}


    public Long getUserId() {return userId;}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public Contact getContact() {
        return contact;
    }

    public Address getAddress() {
        return address;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId !=null && userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    public abstract String toString();

}
