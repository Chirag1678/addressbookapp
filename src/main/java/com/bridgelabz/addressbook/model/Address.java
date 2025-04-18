package com.bridgelabz.addressbook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private long addressId;

    private String houseNo;
    private String Street;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String addressType;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
