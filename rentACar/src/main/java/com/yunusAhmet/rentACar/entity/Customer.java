package com.yunusAhmet.rentACar.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY,mappedBy = "customer")
    private Rental rental;

    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }
}
