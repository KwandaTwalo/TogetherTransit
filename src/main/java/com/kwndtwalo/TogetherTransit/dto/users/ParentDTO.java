package com.kwndtwalo.TogetherTransit.dto.users;

import com.kwndtwalo.TogetherTransit.domain.users.Parent;

public class ParentDTO extends UserDTO {

    private String phone;
    private String email;

    public ParentDTO(Parent parent) {
        super(parent);

        this.phone = parent.getContact() != null
                ? parent.getContact().getPhoneNumber()
                : null;

        this.email = parent.getAuthentication() != null
                ? parent.getAuthentication().getEmailAddress()
                : null;
    }

    @Override
    public String toString() {
        return "\nParentDTO{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                ", accountStatus='" + accountStatus + '\'' +
                ", role='" + role + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}