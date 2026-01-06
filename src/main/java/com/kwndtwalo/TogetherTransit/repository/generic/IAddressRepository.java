package com.kwndtwalo.TogetherTransit.repository.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Long> {

    // Prevent duplicate addresses
    Optional<Address> findByStreetNumberAndStreetNameAndSuburbAndCityAndPostalCode(
            String streetNumber,
            String streetName,
            String suburb,
            String city,
            int postalCode
    );

    boolean existsByStreetNumberAndStreetNameAndSuburbAndCityAndPostalCode(
            String streetNumber,
            String streetName,
            String suburb,
            String city,
            int postalCode
    );

    // Location-based queries
    List<Address> findByCity(String city);

    List<Address> findByCityIgnoreCase(String city);

    List<Address> findBySuburb(String suburb);

    List<Address> findByPostalCode(int postalCode);

    // Admin search
    List<Address> findByStreetNumberAndStreetNameContainingIgnoreCase(String streetNumber, String streetName);

}
