package com.bridgelabz.addressbook.service;

import com.bridgelabz.addressbook.dto.AddressDTO;
import com.bridgelabz.addressbook.dto.ResponseDTO;

public interface AddressService {
    ResponseDTO addAddressToPerson(Long personId, AddressDTO addressDTO);
    ResponseDTO getAddressesByPersonId(Long personId);
    ResponseDTO updateAddress(Long addressId, AddressDTO addressDTO);
    ResponseDTO deleteAddress(Long addressId);
}
