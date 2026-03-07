package com.kwndtwalo.TogetherTransit.service.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.repository.generic.IAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements IAddressService {

    private IAddressRepository addressRepository;

    @Autowired
    public AddressService(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**CRUD OPERATIONS*/

    @Override
    public Address create(Address address) {
        if (address == null) {return null;}

        //Prevent duplicate address entries.
        boolean exists = addressRepository.existsByStreetNumberAndStreetNameAndSuburbAndCityAndPostalCode(
                address.getStreetNumber(),
                address.getStreetName(),
                address.getSuburb(),
                address.getCity(),
                address.getPostalCode()
        );

        if (exists) {
            //return existing address instead of duplicating.
            return addressRepository.findByStreetNumberAndStreetNameAndSuburbAndCityAndPostalCode(
                    address.getStreetNumber(),
                    address.getStreetName(),
                    address.getSuburb(),
                    address.getCity(),
                    address.getPostalCode()
            ).orElse(null);
        }

        return addressRepository.save(address);
    }

    @Override
    public Address read(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Override
    public Address update(Address address) {
        if (address == null || address.getAddressId() == null) {return null;}

        if (!addressRepository.existsById(address.getAddressId())) {return null;}

        return addressRepository.save(address);
    }

    @Override
    public boolean delete(Long id) {
        if (!addressRepository.existsById(id)) {
            return false;
        }
        addressRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    /**CUSTOM METHODS FROM REPOSITORY*/

    public Address findDuplicateAddress(Address address) {
        return addressRepository
                .findByStreetNumberAndStreetNameAndSuburbAndCityAndPostalCode(
                        address.getStreetNumber(),
                        address.getStreetName(),
                        address.getSuburb(),
                        address.getCity(),
                        address.getPostalCode()
                )
                .orElse(null);
    }

    public List<Address> getAddressesByCity(String city) {
        return addressRepository.findByCityIgnoreCase(city);
    }

    public List<Address> getAddressesBySuburb(String suburb) {
        return addressRepository.findBySuburb(suburb);
    }

    public List<Address> getAddressesByPostalCode(int postalCode) {
        return addressRepository.findByPostalCode(postalCode);
    }

    public List<Address> searchByStreet(String streetNumber, String streetName) {
        return addressRepository
                .findByStreetNumberAndStreetNameContainingIgnoreCase(
                        streetNumber,
                        streetName
                );
    }
}
