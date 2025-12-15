package com.kwndtwalo.TogetherTransit.domain.generic;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String streetNumber;
    private String streetName;
    private String suburb;
    private String city;
    private int postalCode;

    protected Address() {}

    private Address(Builder builder) {
        this.addressId = builder.addressId;
        this.streetNumber = builder.streetNumber;
        this.streetName = builder.streetName;
        this.suburb = builder.suburb;
        this.city = builder.city;
        this.postalCode = builder.postalCode;
    }

    public Long getAddressId() {
        return addressId;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getCity() {
        return city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + getAddressId() +
                ", streetNumber='" + getStreetNumber() + '\'' +
                ", streetName='" + getStreetName() + '\'' +
                ", suburb='" + getSuburb() + '\'' +
                ", city='" + getCity() + '\'' +
                ", postalCode=" + getPostalCode() +
                '}';
    }

    public static class Builder {
        private Long addressId;
        private String streetNumber;
        private String streetName;
        private String suburb;
        private String city;
        private int postalCode;

        public Builder addressId(Long addressId) {
            this.addressId = addressId;
            return this;
        }
        public Builder streetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }
        public Builder streetName(String streetName) {
            this.streetName = streetName;
            return this;
        }
        public Builder suburb(String suburb) {
            this.suburb = suburb;
            return this;
        }
        public Builder city(String city) {
            this.city = city;
            return this;
        }
        public Builder postalCode(int postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder copy(Address address) {
            this.addressId = address.getAddressId();
            this.streetNumber = address.getStreetNumber();
            this.streetName = address.getStreetName();
            this.suburb = address.getSuburb();
            this.city = address.getCity();
            this.postalCode = address.getPostalCode();
            return this;
        }
        public Address build() {return new Address(this);}
    }
}
