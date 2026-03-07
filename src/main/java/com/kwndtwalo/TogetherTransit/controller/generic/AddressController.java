package com.kwndtwalo.TogetherTransit.controller.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import com.kwndtwalo.TogetherTransit.dto.generic.AddressDTO;
import com.kwndtwalo.TogetherTransit.service.generic.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /*
     * CREATE ADDRESS
     */
    @PostMapping("/create")
    public ResponseEntity<AddressDTO> create(@RequestBody Address address) {

        Address created = addressService.create(address);


        if (created == null) {
            return ResponseEntity.badRequest().build();
        }

        // Save address and return HTTP 200 (OK)
        return ResponseEntity.ok().body(new AddressDTO(created));
    }

    /*
     * READ ADDRESS BY ID
     * This method fetches a single address using its ID.
     * If not found, returns HTTP 404.
     */
    @GetMapping("/read/{id}")
    public ResponseEntity<Address> read(@PathVariable Long id) {

        Address address = addressService.read(id);

        if (address == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(address);
    }

    /*
     * UPDATE ADDRESS
     * This method updates an existing address.
     * It first validates data using the factory.
     */
    @PutMapping("/update")
    public ResponseEntity<Address> update(@RequestBody Address address) {

        Address updated = addressService.update(address);

        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(updated);
    }

    /*
     * DELETE ADDRESS BY ID
     * Deletes address using ID.
     * Returns true if deleted, false if not found.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {

        boolean deleted = addressService.delete(id);

        return ResponseEntity.ok(deleted);
    }

    /*
     * GET ALL ADDRESSES
     * Returns all address records from database.
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Address>> getAll() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    /*
     * SEARCH ADDRESSES BY CITY
     * Allows searching addresses using city name.
     * Case-insensitive.
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<List<Address>> getByCity(@PathVariable String city) {
        return ResponseEntity.ok(addressService.getAddressesByCity(city));
    }

    /*
     * SEARCH ADDRESSES BY SUBURB
     * Used when filtering addresses by suburb.
     */
    @GetMapping("/suburb/{suburb}")
    public ResponseEntity<List<Address>> getBySuburb(@PathVariable String suburb) {
        return ResponseEntity.ok(addressService.getAddressesBySuburb(suburb));
    }

    /*
     * SEARCH ADDRESSES BY POSTAL CODE
     * Finds all addresses within a specific postal code.
     */
    @GetMapping("/postal/{postalCode}")
    public ResponseEntity<List<Address>> getByPostalCode(@PathVariable int postalCode) {
        return ResponseEntity.ok(addressService.getAddressesByPostalCode(postalCode));
    }

    /*
     * SEARCH BY STREET
     * Used for admin searching: partial street name matching.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Address>> searchStreet(
            @RequestParam String streetNumber,
            @RequestParam String streetName) {

        return ResponseEntity.ok(
                addressService.searchByStreet(streetNumber, streetName)
        );
    }
}