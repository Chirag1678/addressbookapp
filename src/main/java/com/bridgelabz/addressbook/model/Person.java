package com.bridgelabz.addressbook.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long personId;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    @JsonManagedReference
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();
}
