package com.yunusahmet.rentacar.controller;


import com.yunusahmet.rentacar.dto.converter.RentCarDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yunusahmet.rentacar.business.CustomerManager;
import com.yunusahmet.rentacar.dto.*;

import javax.validation.Valid;

@RequestMapping("/customer")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerManager customerManager;
    private final RentCarDtoConverter carDtoConverter;


    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        return new ResponseEntity<>(customerManager.createCustomer(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomerByCustomerId(@PathVariable int customerId) {
        customerManager.deleteCustomerByCustomerId(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CustomerDto> updateCustomer(@Valid @RequestBody UpdateCustomerRequest request) {
        return new ResponseEntity<>(customerManager.updateCustomer(request), HttpStatus.CREATED);
    }

    @GetMapping("/getById")
    public ResponseEntity<RentCarDto> getById(@RequestParam("customerId") int customerId) {
        return new ResponseEntity<>(carDtoConverter.convert(
                customerManager.getCustomerByCustomerId(customerId)),
                HttpStatus.OK);
    }

}
