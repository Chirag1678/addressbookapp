package com.bridgelabz.addressbook.service;

import com.bridgelabz.addressbook.dto.PersonDTO;
import com.bridgelabz.addressbook.dto.ResponseDTO;
import com.bridgelabz.addressbook.model.Address;
import com.bridgelabz.addressbook.model.Person;
import com.bridgelabz.addressbook.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService{
    @Autowired
    private PersonRepository personRepository;

    // method to add person to database
    @Override
    public ResponseDTO addPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setPhoneNumber(personDTO.getPhoneNumber());
        person.setEmail(personDTO.getEmail());

        List<Address> addressList = personDTO.getAddress()
                .stream()
                .map(addressDTO -> {
                    Address address = new Address();
                    address.setHouseNo(addressDTO.getHouseNo());
                    address.setStreet(addressDTO.getStreet());
                    address.setCity(addressDTO.getCity());
                    address.setState(addressDTO.getState());
                    address.setCountry(addressDTO.getCountry());
                    address.setPostalCode(addressDTO.getPostalCode());
                    address.setAddressType(addressDTO.getAddressType());
                    address.setPerson(person);
                    return address;
                })
                .collect(Collectors.toList());

        person.setAddresses(addressList);

        personRepository.save(person);

        return new ResponseDTO("Person added successfully", HttpStatus.CREATED.value(), person);
    }

    // method to get all persons
    @Override
    public ResponseDTO getAllPersons() {
        List<Person> personList = personRepository.findAll();


    }
}
