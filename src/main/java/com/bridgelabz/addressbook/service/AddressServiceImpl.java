package com.bridgelabz.addressbook.service;

import com.bridgelabz.addressbook.dto.AddressDTO;
import com.bridgelabz.addressbook.dto.ResponseDTO;
import com.bridgelabz.addressbook.exceptions.AddressBookException;
import com.bridgelabz.addressbook.model.Address;
import com.bridgelabz.addressbook.model.Person;
import com.bridgelabz.addressbook.repository.AddressRepository;
import com.bridgelabz.addressbook.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonRepository personRepository;

    // method to add a address to a person
    @Override
    public ResponseDTO addAddressToPerson(Long personId, AddressDTO addressDTO) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new AddressBookException("Person not found for id: " + personId));

        Address address = new Address();
        address.setHouseNo(addressDTO.getHouseNo());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setAddressType(addressDTO.getAddressType());
        address.setPerson(person);

        addressRepository.save(address);

        return new ResponseDTO("Address added successfully", HttpStatus.CREATED.value(), person);
    }

    // method to get all addresses of a person
    @Override
    public ResponseDTO getAddressesByPersonId(Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new AddressBookException("Person not found for id: " + personId));

        return new ResponseDTO("Person's addresses fetched successfully", HttpStatus.OK.value(), person.getAddresses());
    }

    // method to update an address by id
    @Override
    public ResponseDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressBookException("Address not found for id: " + addressId));

        address.setHouseNo(addressDTO.getHouseNo());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setAddressType(addressDTO.getAddressType());

        addressRepository.save(address);

        return new ResponseDTO("Address updated successfully", HttpStatus.OK.value(), address);
    }

    // method to delete address by id
    @Override
    public ResponseDTO deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressBookException("Address not found for id: " + addressId));

        addressRepository.delete(address);

        return new ResponseDTO("Address deleted successfully", HttpStatus.OK.value(), null);
    }
}
