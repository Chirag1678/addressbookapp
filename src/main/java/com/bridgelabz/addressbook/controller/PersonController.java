package com.bridgelabz.addressbook.controller;

import com.bridgelabz.addressbook.dto.AddressDTO;
import com.bridgelabz.addressbook.dto.PersonDTO;
import com.bridgelabz.addressbook.dto.ResponseDTO;
import com.bridgelabz.addressbook.service.AddressService;
import com.bridgelabz.addressbook.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @Autowired
    private AddressService addressService;

    // add a person
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addPerson(@Valid @RequestBody PersonDTO personDTO) {
        ResponseDTO responseDTO = personService.addPerson(personDTO);

        return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(responseDTO.getStatusCode()));
    }

    // get all person
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllPersons() {
        ResponseDTO responseDTO = personService.getAllPersons();

        return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(responseDTO.getStatusCode()));
    }

    // get a person details by id
    @GetMapping("/{personId}")
    public ResponseEntity<ResponseDTO> getPersonById(@PathVariable Long personId) {
        ResponseDTO responseDTO = personService.getPersonById(personId);

        return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(responseDTO.getStatusCode()));
    }

    // update person details by id
    @PutMapping("/{personId}")
    public ResponseEntity<ResponseDTO> updatePersonById(@PathVariable Long personId, @Valid @RequestBody PersonDTO personDTO) {
        ResponseDTO responseDTO = personService.updatePersonById(personId, personDTO);

        return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(responseDTO.getStatusCode()));
    }

    // delete a person by id
    @DeleteMapping("/{personId}")
    public ResponseEntity<ResponseDTO> deletePersonById(@PathVariable Long personId) {
        ResponseDTO responseDTO = personService.deletePerson(personId);

        return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(responseDTO.getStatusCode()));
    }

    // add address to a person
    @PostMapping("/{personId}/addresses")
    public ResponseEntity<ResponseDTO> addAddressToPerson(@PathVariable Long personId, @Valid @RequestBody AddressDTO addressDTO) {
        ResponseDTO responseDTO = addressService.addAddressToPerson(personId, addressDTO);

        return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(responseDTO.getStatusCode()));
    }

    // get all addresses of a person
    @GetMapping("/{personId}/addresses")
    public ResponseEntity<ResponseDTO> getAllAddresses(@PathVariable Long personId) {
        ResponseDTO responseDTO = addressService.getAddressesByPersonId(personId);

        return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(responseDTO.getStatusCode()));
    }

    // update the address of a person based on an address type
    @PutMapping("/{personId}/addresses/{addressType}")
    public ResponseEntity<ResponseDTO> updateAddressByType(@PathVariable Long personId, @PathVariable String addressType, @Valid @RequestBody AddressDTO addressDTO) {
        ResponseDTO responseDTO = personService.updateAddressByType(personId, addressType, addressDTO);

        return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(responseDTO.getStatusCode()));
    }

    // delete the address of a person based on an address type
    @DeleteMapping("/{personId}/addresses/{addressType}")
    public ResponseEntity<ResponseDTO> deleteAddressByType(@PathVariable Long personId, @PathVariable String addressType) {
        ResponseDTO responseDTO = personService.deleteAddressByType(personId, addressType);

        return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(responseDTO.getStatusCode()));
    }

    // update an address based on the address id (optional -> extra endpoint)
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<ResponseDTO> updateAddressById(@PathVariable Long addressId, @Valid @RequestBody AddressDTO addressDTO) {
        ResponseDTO responseDTO = addressService.updateAddress(addressId, addressDTO);

        return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(responseDTO.getStatusCode()));
    }

    // delete an address based on the address id (optional -> extra endpoint)
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<ResponseDTO> deleteAddressById(@PathVariable Long addressId) {
        ResponseDTO responseDTO = addressService.deleteAddress(addressId);

        return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(responseDTO.getStatusCode()));
    }
}
