package com.bridgelabz.addressbook.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDTO {
    @NotBlank(message = "House number cannot be empty")
    private String houseNo;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Postal Code is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "Invalid Postal Code")
    private String postalCode;

    @NotBlank(message = "Address type is required")
    private String addressType;
}
