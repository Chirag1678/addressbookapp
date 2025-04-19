package com.bridgelabz.addressbook.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonDTO {
    @NotBlank(message = "First Name is required")
    @Pattern(regexp = "^[A-Z][a-z]{2,}$", message = "Invalid First name")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Pattern(regexp = "^[A-Z][a-z]{2,}$", message = "Invalid Last name")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?(\\d{1,3})?[\\-\\s]?\\d{10}$", message = "Invalid Phone number")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email address")
    private String email;

    @Valid
    @Size(min = 1, message = "At least one address is required")
    private List<AddressDTO> address;
}
