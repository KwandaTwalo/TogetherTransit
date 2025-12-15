package com.kwndtwalo.TogetherTransit.domain.generic;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;
    private String phoneNumber;
    private String emergencyNumber;

    protected Contact() {}

    private Contact(Builder builder) {
        this.contactId = builder.contactId;
        this.phoneNumber = builder.phoneNumber;
        this.emergencyNumber = builder.emergencyNumber;
    }

    public Long getContactId() {
        return contactId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + getContactId() +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", emergencyNumber='" + getEmergencyNumber() + '\'' +
                '}';
    }

    public static class Builder {
        private Long contactId;
        private String phoneNumber;
        private String emergencyNumber;

        public Builder setContactId(Long contactId) {
            this.contactId = contactId;
            return this;
        }
        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        public Builder setEmergencyNumber(String emergencyNumber) {
            this.emergencyNumber = emergencyNumber;
            return this;
        }

        public Builder copy(Contact contact) {
            this.contactId = contact.getContactId();
            this.phoneNumber = contact.getPhoneNumber();
            this.emergencyNumber = contact.getEmergencyNumber();
            return this;
        }
        public Contact build() {return new Contact(this);}
    }
}
