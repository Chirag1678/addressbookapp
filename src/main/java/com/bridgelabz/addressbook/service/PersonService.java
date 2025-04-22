package com.bridgelabz.addressbook.service;

import com.bridgelabz.addressbook.dto.AddressDTO;
import com.bridgelabz.addressbook.dto.PersonDTO;
import com.bridgelabz.addressbook.dto.ResponseDTO;

public interface PersonService {
    ResponseDTO addPerson(PersonDTO personDTO);
    ResponseDTO getAllPersons();
    ResponseDTO getPersonById(Long personId);
    ResponseDTO updatePersonById(Long personId, PersonDTO personDTO);
    ResponseDTO deletePerson(Long personId);
    ResponseDTO updateAddressByType(Long personId, String addressType, AddressDTO addressDTO);
    ResponseDTO deleteAddressByType(Long personId, String addressType);
    ResponseDTO loginPerson(String username, String password);
}
