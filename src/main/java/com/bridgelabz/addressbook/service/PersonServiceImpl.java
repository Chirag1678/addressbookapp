package com.bridgelabz.addressbook.service;

import com.bridgelabz.addressbook.dto.AddressDTO;
import com.bridgelabz.addressbook.dto.PersonDTO;
import com.bridgelabz.addressbook.dto.ResponseDTO;
import com.bridgelabz.addressbook.exceptions.AddressBookException;
import com.bridgelabz.addressbook.model.Address;
import com.bridgelabz.addressbook.model.Person;
import com.bridgelabz.addressbook.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService{
    @Autowired
    private JwtService jwt;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    // method to add a person to a database
    @Override
    public ResponseDTO addPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setUserName(personDTO.getUserName());
        person.setPassword(encoder.encode(personDTO.getPassword()));
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

        // generate token
        String token = jwt.generateToken(person.getPersonId(), personDTO.getUserName());

        return new ResponseDTO("Person added successfully", HttpStatus.CREATED.value(), new Object[]{person, token});
    }

    // method to get all persons
    @Override
    public ResponseDTO getAllPersons() {
        List<Person> personList = personRepository.findAll();

        if(!personList.isEmpty()) return new ResponseDTO("Persons fetched successfully", HttpStatus.OK.value(), personList);
        else throw new AddressBookException("Person database is empty!!");
    }

    // method to get person details based on id
    @Override
    public ResponseDTO getPersonById(Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new AddressBookException("Person not found for id: " + personId));

        return new ResponseDTO("Person details fetched successfully", HttpStatus.OK.value(), person);
    }

    // method to update person by id
    @Transactional
    @Override
    public ResponseDTO updatePersonById(Long personId, PersonDTO personDTO) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new AddressBookException("Person not found for id: " + personId));

        // Update basic details
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setUserName(personDTO.getUserName());
        person.setPassword(encoder.encode(personDTO.getPassword()));
        person.setPhoneNumber(personDTO.getPhoneNumber());
        person.setEmail(personDTO.getEmail());

        person.getAddresses().clear();;

        // create a new address list
        for (AddressDTO addressDTO : personDTO.getAddress()) {
            Address address = new Address();
            address.setHouseNo(addressDTO.getHouseNo());
            address.setStreet(addressDTO.getStreet());
            address.setCity(addressDTO.getCity());
            address.setState(addressDTO.getState());
            address.setCountry(addressDTO.getCountry());
            address.setPostalCode(addressDTO.getPostalCode());
            address.setAddressType(addressDTO.getAddressType());
            address.setPerson(person); // very important for maintaining relation
            person.getAddresses().add(address);
        }

        // Save the updated person
        personRepository.save(person);

        return new ResponseDTO("Person details updated successfully", HttpStatus.OK.value(), person);
    }


    // method to delete person by id
    @Override
    public ResponseDTO deletePerson(Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new AddressBookException("Person not found for id: " + personId));

        personRepository.delete(person);

        return new ResponseDTO("Person details deleted successfully", HttpStatus.OK.value(), null);
    }

    // method to update a person's address based on address type
    @Override
    public ResponseDTO updateAddressByType(Long personId, String addressType, AddressDTO addressDTO) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new AddressBookException("Person not found for id: " + personId));

        List<Address> personAddresses = person.getAddresses();

        boolean addressUpdated = false;

        for(Address address:personAddresses) {
            if(address.getAddressType().equalsIgnoreCase(addressType)) {
                address.setHouseNo(addressDTO.getHouseNo());
                address.setStreet(addressDTO.getStreet());
                address.setCity(addressDTO.getCity());
                address.setState(addressDTO.getState());
                address.setCountry(addressDTO.getCountry());
                address.setPostalCode(addressDTO.getPostalCode());
                addressUpdated = true;
                break;
            }
        }

        if(!addressUpdated) {
            throw new AddressBookException("Address with type: " + addressType + " not found for person id: " + personId);
        }

        personRepository.save(person);

        return new ResponseDTO("Address updated successfully", HttpStatus.OK.value(), null);
    }

    // method to delete an address based on address type
    @Override
    public ResponseDTO deleteAddressByType(Long personId, String addressType) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new AddressBookException("Person not found for id: " + personId));

        List<Address> personAddresses = person.getAddresses();

        boolean addressDeleted = personAddresses.removeIf(address -> address.getAddressType().equalsIgnoreCase(addressType));

        if(!addressDeleted) {
            throw new AddressBookException("Address with type: " + addressType + " not found for person id: " + personId);
        }

        personRepository.save(person);

        return new ResponseDTO("Address of type: " + addressType + " deleted successfully", HttpStatus.OK.value(), null);
    }

    // method to login user
    @Override
    public ResponseDTO loginPerson(String userName, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

        if(authentication.isAuthenticated()) {
            Person person = personRepository.findByUserName(userName)
                    .orElseThrow(() -> new AddressBookException("Invalid credentials. Try again."));

            String token = jwt.generateToken(person.getPersonId(), person.getUserName());

            return new ResponseDTO("Login Success", HttpStatus.OK.value(), token);
        } else {
            throw new AddressBookException("Invalid credentials. Try again.");
        }
    }
}
