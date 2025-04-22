package com.bridgelabz.addressbook.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]+$")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotEmpty(message = "At least one phone number is required")
    private List<@Pattern(regexp = "^\\+?(\\d{1,3})?[\\-\\s]?\\d{10}$", message = "Invalid Phone number") String> phoneNumber;

    @NotEmpty(message = "At least one email is required")
    private List<@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email address") String> email;

    @Valid
    @Size(min = 1, message = "At least one address is required")
    private List<AddressDTO> address;
}
