package com.yunusAhmet.rentACar.dto;

import com.yunusAhmet.rentACar.entity.Rental;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private int customerId;

    private String firstName;

    private String lastName;

    private String email;

}
